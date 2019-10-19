import axios from 'axios'

export async function findAllDataLogs() {
  const response = await axios.get('/api/dataLog/list')
  return response.data
}
