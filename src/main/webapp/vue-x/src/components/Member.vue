<template>
  <div class="hello bs-glyphicons bs-glyphicons-list">
    <div class="card">
      <div class="card-header">
        <h2>會員管理  </h2>
      </div>
      <div class="card-body">
        <h3>members</h3>
        <loading :display="display" :code="code" />
        <div v-if="display === false">
          <table class="table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Operate</th>
              </tr>
              <tr>
                <th></th>
                <th></th>
                <th></th>
                <th></th>
                <th>
                  <!-- <button type="button" class="btn btn-primary" onclick="location.href='member?action=uiAdd'">Add</button>
                  <button type="button" class="btn btn-outline-info" onclick="location.href='member?action=search'">Search</button> -->
                  <button type="button" class="btn btn-primary">Add</button>
                  <button type="button" class="btn btn-outline-info">Search</button>
                </th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="(bean, index) in memberList" :key="index">
                <td>{{ bean.id }}</td>
                <td>{{ bean.name }}</td>
                <td>{{ bean.email }}</td>
                <td>{{ bean.phone }}</td>
                <td>
                  <button type="button" class="btn btn-outline-primary">Edit</button>
                  <button type="button" class="btn btn-outline-danger">Remove</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import loading from './Loading.vue'

export default {
  name: 'Member',
  components: {
    'loading': loading
  },
  data () {
    return {
      member: {
        id: 0,
        name: '',
        email: '',
        phone: '',
        timeModify: 0
      },
      memberList: [],
      message: '',

      // loading
      display: true,
      code: 0
    }
  },
  mounted () {
    this.queryMember()
  },
  methods: {
    // 頁面載入完, 執行方法檢查是否有資訊
    // doRemove (id) {
    //   if (confirm('是否要刪除 id ' + id)) {
    //     window.location.href = 'member?action=remove&id=' + id
    //   }
    // },
    queryMember () {
      var self = this
      axios({
        method: 'post',
        url: '/onAccountX/srv/member/query',
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {
          member: self.member
        }
      }).then(function (response) {
        if (response) {
          self.memberList = response.data.data
          console.log(response)
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query member failed: ', error)
      })
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>
