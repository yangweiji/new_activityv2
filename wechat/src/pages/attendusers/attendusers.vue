<template>
  <div class="page">
    <div v-if="categories" class="page__bd">
      <div class="weui-tab">
        <kytabs :tabs="categories" v-model="activeTab" :tab-width="300" />
        <div class="weui-tab__panel">
          <div class="weui-panel weui-panel_access">
            <div class="weui-panel__bd">
              <div v-for="item in tabItems" :key="item.user_id" class="weui-cell weui-cell_access" hover-class="weui-cell_active">
                <div class="weui-cell__hd">
                  <kyimage default-src="man.png" :src="item.avatar" />
                </div>
                <div class="weui-cell__bd">{{item.displayname}}
                </div>
                <div class="weui-cell__ft">
                  <span>{{item.attend_time}}</span>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import kytabs from "@/components/kytabs.vue";
    import kyimage from "@/components/kyimage.vue";
  export default {
    data() {
      return {
        activityId: null,
        items: [],
        categories: null,
        activeTab: null
      };
    },
    computed: {
      tabItems() {
        var users = [];
        if (this.items) {
          for (var i = 0; i < this.items.length; i++) {
            var item = this.items[i];
            if (item.activity_ticket_id == this.activeTab) {
              users.push(item);
            }
          }
        }
        return users;
      }
    },
    components: {
      kytabs,
      kyimage
    },
    methods: {
      //取得文章信息
      getData() {
        var that = this;
        var param = {
          activityId: that.activityId
        };
        this.$kyutil.get("/pub/wx/activity/attendusers", param).then(res => {
          that.items = res.users
          var categories = []
          for (let index = 0; index < res.tickets.length; index++) {
            const ticket = res.tickets[index];
            var count = 0
            for (let i = 0; i < res.users.length; i++) {
              const user = res.users[i];
              if (user.activity_ticket_id == ticket.id) {
                count++
              }
            }
            categories.push({
              id: ticket.id,
              name: ticket.title,
              number:count
            })
          }
          that.categories = categories
          if (categories.length > 0) {
            that.activeTab = categories[0].id
          }
        })
      },
    },
    created() {
      // console.log("photos created");
    },
    onShow() {
      this.activityId = this.$root.$mp.query.activityId;
      if (this.$store.state.community) {
        wx.setNavigationBarTitle({
          title: this.$store.state.community.name
        })
      }
      this.getData()
    }
  };
</script>

<style scoped>

</style>
