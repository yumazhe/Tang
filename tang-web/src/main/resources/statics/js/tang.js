// 服务器地址
let host = "127.0.0.1"
// let host = "10.7.0.191"
let port = 8081
let host_address = "http://"+host+":"+port

// 用HTML的localStorage本地存储数据
var user_login_key = 'tang_config_user'
// 获取token信息
function getToken() {
    return JSON.parse(window.localStorage.getItem(user_login_key) || '[]')
}

//保存登录token
function saveToken(items){
    window.localStorage.setItem( user_login_key, JSON.stringify(items) )
}

//删除token
function deleteToken(){
    window.localStorage.removeItem(user_login_key)
}

// 判断用户登录是否过期，没有过期则返回用户名称，过期返回空字符串
function expired(){

    var user = getToken()

    if(user === null || user.length === 0){
        return ''
    }else{
        // 存在用户登录信息

        var time = user.expired_time
        
        if(time === undefined || time === ''){
            deleteToken()
            return ''
        }
        
        // js 获取当前时间戳
        let now = new Date().getTime()
        console.log("当前时间：" + now, ", 过期时间："+time)

        if(time > now){
            return user.name
        }else{
            deleteToken()
            return ''
        }

    }

    return ''
}