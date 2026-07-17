package com.hmmp.system.domain;

/**
 * 期刊统计聚合结果
 *
 * @author hmmp
 */
public class StatJournalTotal
{
    /** 统计年份跨度（用于年平均，至少为 1） */
    private Integer yearSpan = 1;

    /** 收稿量 */
    private Long receiveCount = 0L;
    /** 年平均收稿量 */
    private Double avgReceiveCount = 0D;

    /** 发稿量 */
    private Long publishCount = 0L;
    /** 年平均发稿量 */
    private Double avgPublishCount = 0D;
    /** 发稿率 % */
    private Double publishRate = 0D;

    /** 退稿量 */
    private Long rejectCount = 0L;
    /** 年平均退稿量 */
    private Double avgRejectCount = 0D;
    /** 退稿率 % */
    private Double rejectRate = 0D;

    /** 初审退稿量 */
    private Long initialRejectCount = 0L;
    /** 年平均初审退稿量 */
    private Double avgInitialRejectCount = 0D;
    /** 年初审退稿率 % */
    private Double initialRejectRate = 0D;

    /** 评审退稿量 */
    private Long reviewRejectCount = 0L;
    /** 年平均评审退稿量 */
    private Double avgReviewRejectCount = 0D;
    /** 年评审退稿率 % */
    private Double reviewRejectRate = 0D;

    /** 复审退稿量 */
    private Long reReviewRejectCount = 0L;
    /** 年平均复审退稿量 */
    private Double avgReReviewRejectCount = 0D;
    /** 年复审退稿率 % */
    private Double reReviewRejectRate = 0D;

    /** 终审退稿量 */
    private Long finalRejectCount = 0L;
    /** 年平均终审退稿量 */
    private Double avgFinalRejectCount = 0D;
    /** 年终审退稿率 % */
    private Double finalRejectRate = 0D;

    /** 录用量 */
    private Long adoptCount = 0L;
    /** 年平均录用量 */
    private Double avgAdoptCount = 0D;
    /** 录用率 % */
    private Double adoptRate = 0D;

    /** 送审量 */
    private Long sendReviewCount = 0L;
    /** 年平均送审量 */
    private Double avgSendReviewCount = 0D;
    /** 送审率 % */
    private Double sendReviewRate = 0D;

    /** 基金量 */
    private Long fundCount = 0L;
    /** 年平均基金量 */
    private Double avgFundCount = 0D;
    /** 年平均基金率 % */
    private Double fundRate = 0D;

    /** 初审次数 */
    private Long initialReviewTimes = 0L;
    /** 评审次数 */
    private Long peerReviewTimes = 0L;
    /** 复审次数 */
    private Long reReviewTimes = 0L;
    /** 终审次数 */
    private Long finalReviewTimes = 0L;

    public Integer getYearSpan()
    {
        return yearSpan;
    }

    public void setYearSpan(Integer yearSpan)
    {
        this.yearSpan = yearSpan;
    }

    public Long getReceiveCount()
    {
        return receiveCount;
    }

    public void setReceiveCount(Long receiveCount)
    {
        this.receiveCount = receiveCount;
    }

    public Double getAvgReceiveCount()
    {
        return avgReceiveCount;
    }

    public void setAvgReceiveCount(Double avgReceiveCount)
    {
        this.avgReceiveCount = avgReceiveCount;
    }

    public Long getPublishCount()
    {
        return publishCount;
    }

    public void setPublishCount(Long publishCount)
    {
        this.publishCount = publishCount;
    }

    public Double getAvgPublishCount()
    {
        return avgPublishCount;
    }

    public void setAvgPublishCount(Double avgPublishCount)
    {
        this.avgPublishCount = avgPublishCount;
    }

    public Double getPublishRate()
    {
        return publishRate;
    }

    public void setPublishRate(Double publishRate)
    {
        this.publishRate = publishRate;
    }

    public Long getRejectCount()
    {
        return rejectCount;
    }

    public void setRejectCount(Long rejectCount)
    {
        this.rejectCount = rejectCount;
    }

    public Double getAvgRejectCount()
    {
        return avgRejectCount;
    }

    public void setAvgRejectCount(Double avgRejectCount)
    {
        this.avgRejectCount = avgRejectCount;
    }

    public Double getRejectRate()
    {
        return rejectRate;
    }

    public void setRejectRate(Double rejectRate)
    {
        this.rejectRate = rejectRate;
    }

    public Long getInitialRejectCount()
    {
        return initialRejectCount;
    }

    public void setInitialRejectCount(Long initialRejectCount)
    {
        this.initialRejectCount = initialRejectCount;
    }

    public Double getAvgInitialRejectCount()
    {
        return avgInitialRejectCount;
    }

    public void setAvgInitialRejectCount(Double avgInitialRejectCount)
    {
        this.avgInitialRejectCount = avgInitialRejectCount;
    }

    public Double getInitialRejectRate()
    {
        return initialRejectRate;
    }

    public void setInitialRejectRate(Double initialRejectRate)
    {
        this.initialRejectRate = initialRejectRate;
    }

    public Long getReviewRejectCount()
    {
        return reviewRejectCount;
    }

    public void setReviewRejectCount(Long reviewRejectCount)
    {
        this.reviewRejectCount = reviewRejectCount;
    }

    public Double getAvgReviewRejectCount()
    {
        return avgReviewRejectCount;
    }

    public void setAvgReviewRejectCount(Double avgReviewRejectCount)
    {
        this.avgReviewRejectCount = avgReviewRejectCount;
    }

    public Double getReviewRejectRate()
    {
        return reviewRejectRate;
    }

    public void setReviewRejectRate(Double reviewRejectRate)
    {
        this.reviewRejectRate = reviewRejectRate;
    }

    public Long getReReviewRejectCount()
    {
        return reReviewRejectCount;
    }

    public void setReReviewRejectCount(Long reReviewRejectCount)
    {
        this.reReviewRejectCount = reReviewRejectCount;
    }

    public Double getAvgReReviewRejectCount()
    {
        return avgReReviewRejectCount;
    }

    public void setAvgReReviewRejectCount(Double avgReReviewRejectCount)
    {
        this.avgReReviewRejectCount = avgReReviewRejectCount;
    }

    public Double getReReviewRejectRate()
    {
        return reReviewRejectRate;
    }

    public void setReReviewRejectRate(Double reReviewRejectRate)
    {
        this.reReviewRejectRate = reReviewRejectRate;
    }

    public Long getFinalRejectCount()
    {
        return finalRejectCount;
    }

    public void setFinalRejectCount(Long finalRejectCount)
    {
        this.finalRejectCount = finalRejectCount;
    }

    public Double getAvgFinalRejectCount()
    {
        return avgFinalRejectCount;
    }

    public void setAvgFinalRejectCount(Double avgFinalRejectCount)
    {
        this.avgFinalRejectCount = avgFinalRejectCount;
    }

    public Double getFinalRejectRate()
    {
        return finalRejectRate;
    }

    public void setFinalRejectRate(Double finalRejectRate)
    {
        this.finalRejectRate = finalRejectRate;
    }

    public Long getAdoptCount()
    {
        return adoptCount;
    }

    public void setAdoptCount(Long adoptCount)
    {
        this.adoptCount = adoptCount;
    }

    public Double getAvgAdoptCount()
    {
        return avgAdoptCount;
    }

    public void setAvgAdoptCount(Double avgAdoptCount)
    {
        this.avgAdoptCount = avgAdoptCount;
    }

    public Double getAdoptRate()
    {
        return adoptRate;
    }

    public void setAdoptRate(Double adoptRate)
    {
        this.adoptRate = adoptRate;
    }

    public Long getSendReviewCount()
    {
        return sendReviewCount;
    }

    public void setSendReviewCount(Long sendReviewCount)
    {
        this.sendReviewCount = sendReviewCount;
    }

    public Double getAvgSendReviewCount()
    {
        return avgSendReviewCount;
    }

    public void setAvgSendReviewCount(Double avgSendReviewCount)
    {
        this.avgSendReviewCount = avgSendReviewCount;
    }

    public Double getSendReviewRate()
    {
        return sendReviewRate;
    }

    public void setSendReviewRate(Double sendReviewRate)
    {
        this.sendReviewRate = sendReviewRate;
    }

    public Long getFundCount()
    {
        return fundCount;
    }

    public void setFundCount(Long fundCount)
    {
        this.fundCount = fundCount;
    }

    public Double getAvgFundCount()
    {
        return avgFundCount;
    }

    public void setAvgFundCount(Double avgFundCount)
    {
        this.avgFundCount = avgFundCount;
    }

    public Double getFundRate()
    {
        return fundRate;
    }

    public void setFundRate(Double fundRate)
    {
        this.fundRate = fundRate;
    }

    public Long getInitialReviewTimes()
    {
        return initialReviewTimes;
    }

    public void setInitialReviewTimes(Long initialReviewTimes)
    {
        this.initialReviewTimes = initialReviewTimes;
    }

    public Long getPeerReviewTimes()
    {
        return peerReviewTimes;
    }

    public void setPeerReviewTimes(Long peerReviewTimes)
    {
        this.peerReviewTimes = peerReviewTimes;
    }

    public Long getReReviewTimes()
    {
        return reReviewTimes;
    }

    public void setReReviewTimes(Long reReviewTimes)
    {
        this.reReviewTimes = reReviewTimes;
    }

    public Long getFinalReviewTimes()
    {
        return finalReviewTimes;
    }

    public void setFinalReviewTimes(Long finalReviewTimes)
    {
        this.finalReviewTimes = finalReviewTimes;
    }
}
