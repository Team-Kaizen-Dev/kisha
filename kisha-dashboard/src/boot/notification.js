// leave the export, even if you don't use it
export default ({app, router, Vue}) => {
    Vue.mixin({
        methods: {
            notifySuccess(message) {
                this.notify({
                    type: 'positive',
                    position: 'top-right',
                    message
                })
            },
            notifyError(error) {
                if (typeof error === 'string') {
                    this.$q.notify({
                        color: 'red',
                        position: 'top-right',
                        message: error
                    })
                    return
                }

                if (error.response.status === 401) {
                    return
                }
                let message = ''
                if (error.response !== null && typeof error.response.data === 'string') {
                    message = error.response.data
                } else if (error.response.data.path === '/user/management/changePassword') {
                    message = error.response.data.message
                }
                this.$q.notify({
                    color: 'red',
                    position: 'top-right',
                    message
                })
            },
            notify({type, message}) {
                this.$q.notify({
                    color: type,
                    position: 'top-right',
                    message: message,
                    actions: [
                        {
                            label: 'Close',
                            color: 'white',
                            handler: () => {
                            }
                        },

                    ]
                })
            }
        }
    })
}
