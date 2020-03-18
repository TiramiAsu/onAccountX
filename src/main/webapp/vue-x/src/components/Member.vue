<template>
  <div>
    <div style="padding-top: 1%">
      <div>
        <h1>Members</h1>
        <h6 style="color: lightgray">
          <span v-if="thisLayout === PARAMS.Layout.Manage.value">{{ PARAMS.Layout.Manage.text }}</span>
          <span v-if="thisLayout === PARAMS.Layout.Add.value">{{ PARAMS.Layout.Add.text }}</span>
          <span v-if="thisLayout === PARAMS.Layout.Edit.value">{{ PARAMS.Layout.Edit.text }}</span>
        </h6>
      </div>
      <br />
      <div>
        <!-- Manage UI -->
        <div v-if="thisLayout === PARAMS.Layout.Manage.value">
          <span>
            <loading :display="display" :code="code" />
          </span>
          <div v-if="display === false">
            <table class="table">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Name</th>
                  <th>Email</th>
                  <th>Phone</th>
                  <th>Time Modify</th>
                  <th>Operate</th>
                </tr>
                <tr>
                  <th></th>
                  <th><input v-model="member.name" @change="queryMember()" type="text" class="form-control"></th>
                  <th><input v-model="member.email" @change="queryMember()" type="text" class="form-control"></th>
                  <th><input v-model="member.phone" @change="queryMember()" type="text" class="form-control"></th>
                  <th></th>
                  <th>
                    <!-- <button type="button" class="btn btn-primary" onclick="location.href='member?action=uiAdd'">Add</button>
                    <button type="button" class="btn btn-outline-info" onclick="location.href='member?action=search'">Search</button> -->
                    <button type="button" class="btn btn-primary" @click="mainFunction('add', null)">Add</button>
                    &nbsp;
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
                  <td>{{ toFormatDateTime(bean.timeModify) }}</td>
                  <td>
                    <button type="button" class="btn btn-outline-primary" @click="mainFunction('edit', bean)">Edit</button>
                    <button type="button" class="btn btn-outline-danger" @click="mainFunction('remove', bean)">Remove</button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>

        <!-- Add/Edit UI -->
        <div v-if="thisLayout !== PARAMS.Layout.Manage.value">
          <form class="was-validated">

            <div>
              <label for="memberName">Member Name</label>
              <input v-model="member.name" type="text" class="form-control is-invalid" id="memberName" placeholder="Name" required>
              <div v-if="validate.name" class="invalid-feedback">不可空白</div>
            </div>
            <br />

            <div>
              <label for="memberEmail">Email</label>
              <input v-model="member.email" type="email" class="form-control is-invalid" id="memberEmail" placeholder="example@gmail.com" required>
              <div v-if="validate.email" class="invalid-feedback">不可空白 / 格式錯誤</div>
            </div>
            <br />

            <div>
              <label for="memberPhone">Phone</label>
              <input v-model="member.phone" type="text" class="form-control is-invalid" id="memberPhone" placeholder="Phone" required>
              <div v-if="validate.phone" class="invalid-feedback">不可空白 / 格式錯誤</div>
            </div>
            <br />

          </form>
          <button type="button" class="btn btn-outline-dark" @click="mainFunction('cancel', null)">Cancel</button>
          <button v-if="thisLayout === PARAMS.Layout.Add.value"
                  type="button" class="btn btn-outline-primary" @click="mainFunction('finish', null)">Finish</button>
          <button v-if="thisLayout === PARAMS.Layout.Edit.value"
                  type="button" class="btn btn-outline-primary" @click="mainFunction('update', null)">Update</button>
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
      PARAMS: {
        Layout: {
          Manage: { value: 0, text: '會員維護' },
          Add: { value: 1, text: '會員新增' },
          Edit: { value: 2, text: '會員編輯' }
        }
      },
      member: {
        id: 0,
        name: '',
        email: '',
        phone: '',
        timeModify: 0
      },
      memberList: [],
      message: '',

      // layout
      thisLayout: 0,

      // loading
      display: true,
      code: 0,

      // CSS
      validate: {
        name: false,
        email: false,
        phone: false
      }
    }
  },
  mounted () {
    this.queryMember()
  },
  updated () {
    this.validate.name = !(this.member.name !== '')
    this.validate.email = !(this.member.email !== '' && /\S+@\S+\.\S+/.test(this.member.email))
    this.validate.phone = !(this.member.phone !== '' && /[0]{1}\d{9}/.test(this.member.phone))
  },
  methods: {
    mainFunction (slosilo, bean) {
      var self = this
      var layout = this.PARAMS.Layout
      if (slosilo) {
        switch (slosilo) {
          case 'add':
            self.initBean()
            self.thisLayout = layout.Add.value
            break
          case 'finish':
            self.createMember(self.member)
            self.thisLayout = layout.Manage.value
            self.initBean()
            break
          case 'edit':
            self.member.id = bean.id
            self.member.name = bean.name
            self.member.email = bean.email
            self.member.phone = bean.phone

            self.thisLayout = layout.Edit.value
            break
          case 'update':
            self.updateMember(self.member)
            self.thisLayout = layout.Manage.value
            self.initBean()
            break
          case 'cancel':
            self.thisLayout = layout.Manage.value
            self.initBean()
            break
          case 'remove':
            if (confirm('確定要刪除 id: ' + bean.id + ' ?')) {
              self.deleteMember(bean.id)
              self.initBean()
            } else {
              alert('已取消刪除 id: ' + bean.id)
            }
            break
          case 'search':
            break
        }
      } else {
        console.log('>>> Error, slosilo is null <<<')
      }
    },
    initBean () {
      this.member = {
        id: 0,
        name: '',
        email: '',
        phone: '',
        timeModify: 0
      }
    },
    toApiBean (bean) {
      return {
        id: bean.id,
        name: bean.name,
        email: bean.email,
        phone: bean.phone
      }
    },
    // 頁面載入完, 執行方法檢查是否有資訊
    // doRemove (id) {
    //   if (confirm('是否要刪除 id ' + id)) {
    //     window.location.href = 'member?action=remove&id=' + id
    //   }
    // },
    queryMember () {
      var self = this
      var apiMember = {
        name: (self.member.name !== '') ? self.member.name : '',
        email: (self.member.email !== '') ? self.member.email : '',
        phone: (self.member.phone !== '') ? self.member.phone : ''
      }
      axios({
        method: 'post',
        url: '/onAccountX/srv/member',
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {
          member: apiMember
        }
      }).then(function (response) {
        if (response) {
          self.memberList = response.data.data
        }
        self.display = false
      }).catch(function (error) {
        console.log('>>> Error: query member failed: ', error)
      })
    },
    createMember (bean) {
      var self = this
      var mbr = this.toApiBean(bean)
      axios({
        method: 'put',
        url: '/onAccountX/srv/member',
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {
          Member: mbr
        }
      }).then(function (response) {
        if (response) {
          self.queryMember()
        }
      }).catch(function (error) {
        console.log('>>> Error: Add member failed: ', error)
      })
    },
    updateMember (bean) {
      var self = this
      var mbr = this.toApiBean(bean)
      axios({
        method: 'put',
        url: '/onAccountX/srv/member/' + mbr.id,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {
          Member: mbr
        }
      }).then(function (response) {
        if (response) {
          self.queryMember()
        }
      }).catch(function (error) {
        console.log('>>> Error: Edit member failed: ', error)
      })
    },
    deleteMember (id) {
      var self = this
      axios({
        method: 'delete',
        url: '/onAccountX/srv/member/' + id,
        headers: {
          'Content-Type': 'application/json',
          'mac': 'helloJWT'
        },
        data: {}
      }).then(function (response) {
        if (response) {
          self.queryMember()
        }
      }).catch(function (error) {
        console.log('>>> Error: Delete member failed: ', error)
      })
    },

    /* 時間格式 */

    // 取得格式化(YYY-MM-DD HH:mm)後的日期時間字串
    toFormatDateTime (timestamp, formatStr) {
      /* 1: timestamp 傳入是日期(string)   '2019-7-12 9:17', 則回傳 '2019-07-12 09:17'
       * 2: timestamp 傳入是時間戳(number) 1562894220000, 則回傳 '2019-07-12 09:17'
       * 3: timestamp 傳入是空的(null)     (null), 則回傳 'null'
       * p.s. formatStr 為 Y(年) M(月) D(日) H(時) m(分) s(秒) 組成 */
      // 變成 Date 物件，再拿時間戳(getTime() 回傳的是 number 型態)
      var fmtStr = (formatStr === undefined || formatStr === null) ? 'YYYY-MM-DD HH:mm' : formatStr
      if (timestamp) {
        switch (typeof timestamp) {
          case 'number':
            return moment(timestamp).format(fmtStr)
          case 'string':
            timestamp = new Date(timestamp).getTime()
            return moment(timestamp).format(fmtStr)
          default:
            console.log('Error: 時間轉換型態必須是 Number 或 String')
        }
      }
      return null
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h1, h2, h6, span {
  font-weight: normal;
  text-align: center;
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
