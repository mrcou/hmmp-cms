import { reactive } from 'vue';

import { requestClient } from '#/api/request';

interface CaptchaState {
  enabled: boolean;
  img: string;
  uuid: string;
}

interface CaptchaImageResult {
  captchaEnabled: boolean;
  img?: string;
  uuid?: string;
}

const state = reactive<CaptchaState>({
  enabled: false,
  img: '',
  uuid: '',
});

async function refreshCaptcha() {
  const res = await requestClient.get<CaptchaImageResult>('/captchaImage');
  state.enabled = res.captchaEnabled;
  state.uuid = res.uuid ?? '';
  state.img = res.img ? `data:image/jpg;base64,${res.img}` : '';
}

export function useCaptcha() {
  return { refreshCaptcha, state };
}
