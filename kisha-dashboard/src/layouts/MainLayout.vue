<template>
    <q-layout view="hHh LpR fFf">
        <q-header elevated>
            <q-toolbar class="text-green-9 bg-white">
                <q-btn dense flat round icon="menu" @click="miniState = !miniState"/>
                <q-toolbar-title>
                    Date Input
                    <!--add search bar for location?-->
                </q-toolbar-title>
                <q-btn dense flat round icon="far fa-bell" @click="rightDrawerOpen = !rightDrawerOpen"/>
            </q-toolbar>
        </q-header>
        <q-drawer
                v-model="leftDrawerOpen"
                bordered
                :width="250"
                :breakpoint="1024"
                content-class="bg-grey-1"
                :mini="miniState">
            <div class="full-height">
                <q-list>
                    <q-btn flat
                    />
                    <q-item>
                        <q-item-section avatar>
                            <q-icon name="fa fa-file-invoice"/>
                        </q-item-section>
                        <q-item-section>
                            <q-item-label>Delivery Receipts</q-item-label>
                        </q-item-section>
                    </q-item>
                </q-list>
            </div>
        </q-drawer>

        <q-page-container style="background: #f5f9fc">
            <router-view/>
        </q-page-container>
        <q-drawer
                content-class="bg-grey-1"
                v-model="rightDrawerOpen"
                side="right"
                :width="225"
                :breakpoint="500"
                bordered>
            <div class="full-height">
                <NotificationList/>
            </div>
        </q-drawer>
    </q-layout>
</template>

<script>
  import NotificationList from '../components/NotificationList'

  export default {
    name: 'MainLayout',
    components: {
      NotificationList
    },
    data() {
      return {
        leftDrawerOpen: true,
        rightDrawerOpen: false,
        miniState: true,
      }
    },
    computed: {},
    methods: {}
  }
</script>
