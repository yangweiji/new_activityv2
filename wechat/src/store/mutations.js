import * as type from './mutation-types';
const mutations = {
  [type.SET_MPVUEINFO](state, mpvueInfo) { // eslint-disable-line
    state.mpvueInfo = mpvueInfo;
  },

  [type.SET_SESSIONINFO](state, sessionInfo) { // eslint-disable-line
    state.sessionInfo = sessionInfo;
  },
}

export default mutations;
