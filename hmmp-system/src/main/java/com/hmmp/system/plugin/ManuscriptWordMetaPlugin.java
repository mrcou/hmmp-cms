package com.hmmp.system.plugin;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import com.hmmp.common.utils.StringUtils;

/**
 * 稿件 Word 元信息扫描插件。
 * <p>
 * 前期基于 Apache POI 解析 docx 正文，按学术稿件常见版式提取：
 * 标题、英文标题、关键词、英文关键词、摘要。后续可替换为更专业的 OCR/版面识别实现。
 */
public final class ManuscriptWordMetaPlugin
{
    private static final Pattern CN_KEYWORD_LABEL = Pattern.compile(
        "(?m)^\\s*(关键词|关键字)\\s*[:：]\\s*(.+)$");
    private static final Pattern EN_KEYWORD_LABEL = Pattern.compile(
        "(?mi)^\\s*(Key\\s*words?|Keywords?)\\s*[:：]\\s*(.+)$");
    private static final Pattern CN_ABSTRACT_LABEL = Pattern.compile(
        "(?m)^\\s*(摘\\s*要)\\s*[:：]?\\s*(.*)$");
    private static final Pattern EN_ABSTRACT_LABEL = Pattern.compile(
        "(?mi)^\\s*(Abstract)\\s*[:：]?\\s*(.*)$");
    /** 英文标题行：以拉丁字母为主、可含空格/标点 */
    private static final Pattern MOSTLY_LATIN_LINE = Pattern.compile(
        "^[\\sA-Za-z0-9,.;:'\"\\-()\\[\\]/&/]+$");

    private ManuscriptWordMetaPlugin()
    {
    }

    /**
     * 扫描 Word 文件并提取稿件元信息
     *
     * @param absolutePath 本地绝对路径
     * @return 提取结果
     */
    public static Map<String, Object> scan(String absolutePath) throws Exception
    {
        if (StringUtils.isEmpty(absolutePath))
        {
            throw new IllegalArgumentException("文件路径不能为空");
        }
        String lower = absolutePath.toLowerCase();
        if (!lower.endsWith(".docx"))
        {
            throw new IllegalArgumentException("当前仅支持 .docx 格式，请上传 Word 文档后重试");
        }

        String text = readDocxText(absolutePath);
        return extractMeta(text);
    }

    private static String readDocxText(String absolutePath) throws Exception
    {
        StringBuilder sb = new StringBuilder();
        try (InputStream in = new FileInputStream(absolutePath);
             XWPFDocument doc = new XWPFDocument(in))
        {
            for (XWPFParagraph paragraph : doc.getParagraphs())
            {
                String line = StringUtils.trim(paragraph.getText());
                if (StringUtils.isNotEmpty(line))
                {
                    sb.append(line).append('\n');
                }
            }
        }
        return sb.toString().trim();
    }

    static Map<String, Object> extractMeta(String text)
    {
        Map<String, Object> result = new LinkedHashMap<>();
        String title = extractTitle(text);
        result.put("title", title);
        result.put("enTitle", extractEnTitle(text, title));
        result.put("keywords", splitKeywords(extractByLabel(text, CN_KEYWORD_LABEL)));
        result.put("enKeywords", splitKeywords(extractByLabel(text, EN_KEYWORD_LABEL)));
        result.put("abstractText", extractBlock(text, CN_ABSTRACT_LABEL, EN_ABSTRACT_LABEL, CN_KEYWORD_LABEL));
        result.put("enAbstract", extractBlock(text, EN_ABSTRACT_LABEL, CN_KEYWORD_LABEL, EN_KEYWORD_LABEL));
        result.put("rawPreview", StringUtils.substring(text, 0, 500));
        return result;
    }

    private static String extractTitle(String text)
    {
        String[] lines = text.split("\\R");
        for (String line : lines)
        {
            String trimmed = StringUtils.trim(line);
            if (StringUtils.isEmpty(trimmed))
            {
                continue;
            }
            if (looksLikeLabel(trimmed))
            {
                continue;
            }
            // 标题通常较短，过滤过长段落
            if (trimmed.length() <= 120)
            {
                return trimmed;
            }
            return StringUtils.substring(trimmed, 0, 120);
        }
        return "";
    }

    /**
     * 在中文标题之后，取首个「以英文为主」的短行作为英文标题。
     * 常见版式：中文标题 → 英文标题 → 作者/单位 → 摘要。
     */
    private static String extractEnTitle(String text, String cnTitle)
    {
        String[] lines = text.split("\\R");
        boolean afterCnTitle = StringUtils.isEmpty(cnTitle);
        for (String line : lines)
        {
            String trimmed = StringUtils.trim(line);
            if (StringUtils.isEmpty(trimmed))
            {
                continue;
            }
            if (!afterCnTitle)
            {
                if (trimmed.equals(cnTitle) || trimmed.startsWith(cnTitle))
                {
                    afterCnTitle = true;
                }
                continue;
            }
            if (looksLikeLabel(trimmed))
            {
                break;
            }
            if (!looksLikeEnglishTitle(trimmed))
            {
                // 常见夹在中英文标题之间的作者行（含中文）则继续往后找
                if (containsHan(trimmed))
                {
                    continue;
                }
                break;
            }
            if (trimmed.length() <= 350)
            {
                return trimmed;
            }
            return StringUtils.substring(trimmed, 0, 350);
        }
        return "";
    }

    private static boolean looksLikeEnglishTitle(String line)
    {
        if (line.length() < 8 || line.length() > 350)
        {
            return false;
        }
        if (!MOSTLY_LATIN_LINE.matcher(line).matches())
        {
            return false;
        }
        int letters = 0;
        for (int i = 0; i < line.length(); i++)
        {
            char c = line.charAt(i);
            if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
            {
                letters++;
            }
        }
        // 英文标题通常有多个单词；过滤过短或纯符号行
        return letters >= 8 && line.contains(" ");
    }

    private static boolean containsHan(String line)
    {
        for (int i = 0; i < line.length(); i++)
        {
            if (Character.UnicodeScript.of(line.charAt(i)) == Character.UnicodeScript.HAN)
            {
                return true;
            }
        }
        return false;
    }

    private static boolean looksLikeLabel(String line)
    {
        return CN_KEYWORD_LABEL.matcher(line).find()
            || EN_KEYWORD_LABEL.matcher(line).find()
            || CN_ABSTRACT_LABEL.matcher(line).find()
            || EN_ABSTRACT_LABEL.matcher(line).find()
            || line.matches("(?i)^(introduction|references|参考文献|引言).*");
    }

    private static String extractByLabel(String text, Pattern labelPattern)
    {
        Matcher matcher = labelPattern.matcher(text);
        if (!matcher.find())
        {
            return "";
        }
        return StringUtils.trim(matcher.group(2));
    }

    private static String extractBlock(String text, Pattern startPattern, Pattern... stopPatterns)
    {
        Matcher start = startPattern.matcher(text);
        if (!start.find())
        {
            return "";
        }
        int contentStart = start.end();
        String sameLine = StringUtils.trim(start.group(2));
        String rest = text.substring(contentStart);
        int stopAt = rest.length();
        for (Pattern stopPattern : stopPatterns)
        {
            Matcher stop = stopPattern.matcher(rest);
            if (stop.find())
            {
                stopAt = Math.min(stopAt, stop.start());
            }
        }
        String body = StringUtils.trim(rest.substring(0, stopAt));
        if (StringUtils.isNotEmpty(sameLine))
        {
            return StringUtils.isEmpty(body) ? sameLine : sameLine + "\n" + body;
        }
        return body;
    }

    private static List<String> splitKeywords(String raw)
    {
        List<String> list = new ArrayList<>();
        if (StringUtils.isEmpty(raw))
        {
            return list;
        }
        String normalized = raw
            .replace('；', '\n')
            .replace(';', '\n')
            .replace('，', '\n')
            .replace(',', '\n')
            .replace('、', '\n')
            .replace('|', '\n');
        for (String part : normalized.split("\\R|\\s{2,}"))
        {
            String item = StringUtils.trim(part);
            if (StringUtils.isNotEmpty(item) && !list.contains(item))
            {
                list.add(item);
            }
        }
        return list;
    }
}
