import {STORE_DATA_LOG_MODULE} from './constant'

export default {
  [STORE_DATA_LOG_MODULE.GET_BY_DATE]: state => {
    return state[STORE_DATA_LOG_MODULE.DATA_LOGS]
  },
}
