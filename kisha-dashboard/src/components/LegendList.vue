<template>
    <q-list>
        <q-item-label header>Legend</q-item-label>
        <template v-for="type in types">
            <q-separator></q-separator>
            <q-item clickable @click="toggleAllLevels(type)">
                <q-item-section avatar>
                    <q-icon :name="type.icon" :color="getTypeIconColor(type)"/>
                </q-item-section>
                <q-item-section>
                    <q-item-label>{{type.label}}</q-item-label>
                </q-item-section>
            </q-item>
            <q-item v-if="!mini" class="row">
                <div class="row">
                    <q-chip v-for="level in type.levels"
                            :key="level.id"
                            square
                            dense
                            :selected.sync="selectedTypes[level.id]"
                            :color="selectedTypes[level.id] ? getColor(level.label) : 'grey'"
                            text-color="white"
                            icon="close">
                        {{level.label}}
                    </q-chip>
                </div>
            </q-item>
        </template>
    </q-list>
</template>

<script>
  export default {
    props: {
      mini: {
        type: Boolean,
        default: false
      }
    },
    data() {
      return {
        selectedTypes: {
          '0': true,
          '1': true,
          '2': true,
          '3': true,
          '4': true,
          '5': true,
          '6': true,
          '7': true,
          '8': true,
          '9': true,
          '10': true,
          '11': true,
          '12': true,
          '13': true,
          '14': true
        },
        types: [
          {
            icon: 'fas fa-fire',
            label: 'Fire',
            levels: [
              {
                id: 0,
                label: 'Low'
              },
              {
                id: 1,
                label: 'Moderate'
              },
              {
                id: 2,
                label: 'Severe'
              },
            ]
          },
          {
            icon: 'fas fa-mountain',
            label: 'Earthquake',
            levels: [
              {
                id: 3,
                label: 'Low'
              },
              {
                id: 4,
                label: 'Moderate'
              },
              {
                id: 5,
                label: 'Severe'
              },
            ]
          },
          {
            icon: 'fas fa-water',
            label: 'Flood',
            levels: [
              {
                id: 6,
                label: 'Low'
              },
              {
                id: 7,
                label: 'Moderate'
              },
              {
                id: 8,
                label: 'Severe'
              },
            ]
          },
          {
            icon: 'fas fa-wind',
            label: 'Typhoon',
            levels: [
              {
                id: 9,
                label: 'Low'
              },
              {
                id: 10,
                label: 'Moderate'
              },
              {
                id: 11,
                label: 'Severe'
              },
            ]
          },
          {
            icon: 'fas fa-exclamation-circle',
            label: 'Other',
            levels: [
              {
                id: 12,
                label: 'Low'
              },
              {
                id: 13,
                label: 'Moderate'
              },
              {
                id: 14,
                label: 'Severe'
              },
            ]
          }
        ]
      }
    },
    methods: {
      getColor(level) {
        if (level === "Low") {
          return 'green'
        } else if (level === "Moderate") {
          return 'warning'
        } else if (level === "Severe") {
          return 'negative'
        }
      },
      toggleAllLevels(type) {
        type.levels.forEach(level => {
          this.selectedTypes[level.id] = !this.selectedTypes[level.id]
        })
      },
      getTypeIconColor(type) {
        let typeLevels = type.levels.map(level => this.selectedTypes[level.id])
      },
    },

  }
</script>
