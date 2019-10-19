import {STORE_DATA_LOG_MODULE} from './constant'

export default {
  [STORE_DATA_LOG_MODULE.SAVE_ALL]: (state, dataLogs) => {
    state[STORE_DATA_LOG_MODULE.DATA_LOGS] = dataLogs
  },
}
