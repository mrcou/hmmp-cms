export interface ManuscriptAuthor {
  id: string;
  isContactAuthor: boolean;
  isFirstAuthor: boolean;
  realName: string;
  pinYin: string;
  sex: string;
  birthday: string;
  unit: string;
  enUnit: string;
  address: string;
  postcode: string;
  country: string;
  province: string;
  major: string;
  education: string;
  title: string;
  tel: string;
  mobile: string;
  email: string;
  resume: string;
}

export interface ManuscriptInfoForm {
  title: string;
  enTitle: string;
  keywords: string[];
  enKeywords: string[];
  abstractText: string;
  enAbstract: string;
  fundProject: string;
  enFundProject: string;
  studyClassCode: string;
  clcNumber: string;
  pageNum: number | null;
  remark: string;
  authors: ManuscriptAuthor[];
}

let authorSeq = 0;

export function createAuthor(
  partial?: Partial<ManuscriptAuthor>,
): ManuscriptAuthor {
  authorSeq += 1;
  return {
    id: `author-${authorSeq}`,
    isContactAuthor: false,
    isFirstAuthor: false,
    realName: '',
    pinYin: '',
    sex: '',
    birthday: '',
    unit: '',
    enUnit: '',
    address: '',
    postcode: '',
    country: '中国',
    province: '',
    major: '',
    education: '',
    title: '',
    tel: '',
    mobile: '',
    email: '',
    resume: '',
    ...partial,
  };
}

export function createEmptyManuscriptInfo(): ManuscriptInfoForm {
  return {
    title: '',
    enTitle: '',
    keywords: [],
    enKeywords: [],
    abstractText: '',
    enAbstract: '',
    fundProject: '',
    enFundProject: '',
    studyClassCode: '',
    clcNumber: '',
    pageNum: null,
    remark: '',
    authors: [createAuthor({ isContactAuthor: true, isFirstAuthor: true })],
  };
}
