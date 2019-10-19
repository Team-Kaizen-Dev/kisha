import {STORE_DATA_LOG_MODULE} from './constant'
import {findAllDataLogs} from '../../api/dataLog'

export default {
    [STORE_DATA_LOG_MODULE.FIND_ALL]: ({commit}) => {
        return new Promise(async (resolve, reject) => {
            try {
                const dataLogs = await findAllDataLogs()
                commit(STORE_DATA_LOG_MODULE.SAVE_ALL, dataLogs)
                resolve(dataLogs)
            } catch (error) {
                reject(error)
            }
        })
    },
}
