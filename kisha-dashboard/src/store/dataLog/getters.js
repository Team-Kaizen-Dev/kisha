import {STORE_DATA_LOG_MODULE} from './constant'

export default {
  [STORE_DATA_LOG_MODULE.GET_BY_DATE]: state => {
    return payload => {
      return state[STORE_DATA_LOG_MODULE.DATA_LOGS].filter(el => {
        return el.dateCreated <= payload.endDate && el.dateCreated >= payload.startDate
      })

    }
  },
}
