
// var url = "http://localhost:8080/news";
var url = "http://118.25.130.181/news";
// 读取文章的全部内容
function showArticle(articleId) {
    location.href = "detail.html?id=" + articleId;
}

// html剔除富文本标签，留下纯文本
function getSimpleText(html){
    var re1 = new RegExp("<.+?>","g");//匹配html标签的正则表达式，"g"是搜索匹配多个符合的内容
    var msg = html.replace(re1,'');//执行替换成空字符
    return msg;
}

// 省略内容，用于显示内容简介
Vue.prototype.desc = function (value) {
    if (!value) return ''; //如果为空，则返回空字符串
    value = getSimpleText(value.toString());
    if (value.length > 180)
        return value.substring(0, 180) + "......";
    return value;
};

// 载入菜单栏
var menu = new Vue({
    el: 'catalog',
    data: {
        catalogs: []
    },
    mounted: function () {
        var self = this;
        axios.get(url + "/loadCatalogs").then(function (response) {
            var json = response.data;
            self.catalogs = json;
        })
    }
});

// 导航栏组件
Vue.component('catalog', {
    template:   '<header>\n' +
    '        <div id="mnav">\n' +
    '            <h2>\n' +
    '                <span class="navicon"></span>\n' +
    '            </h2>\n' +
    '            <ul>\n' +
    '                <li>\n' +
    '                    <a href="index.html">主页</a>\n' +
    '                </li>\n' +
    '                <li v-for="catalog in catalogs">\n' +
    '                    <a v-bind:href=\'catalog.url\'>{{catalog.name}}</a>\n' +
    '                </li>\n' +
    '            </ul>\n' +
    '        </div>\n' +
    '        <div class="topnav">\n' +
    '            <nav>\n' +
    '                <ul>\n' +
    '                    <li>\n' +
    '                        <a href="index.html">主页</a>\n' +
    '                    </li>\n' +
    '                    <li v-for="catalog in catalogs">\n' +
    '                        <a v-bind:href=\'catalog.url\'>{{catalog.name}}</a>\n' +
    '                    </li>\n' +
    '                </ul>\n' +
    '            </nav>\n' +
    '        </div>\n' +
    '    </header>',
    props: ['catalogs'],
    // method: {
    //     change: function () {
    //         var oH2 = document.getElementsByTagName("h2")[0];
    //         var oUl = document.getElementsByTagName("ul")[0];
    //         var style = oUl.style;
    //         style.display = style.display == "block" ? "none" : "block";
    //         oH2.className = style.display == "block" ? "open" : ""
    //     }
    // }
    mounted: function () {
            var oH2 = document.getElementsByTagName("h2")[0];
            var oUl = document.getElementsByTagName("ul")[0];
            oH2.onclick = function () {
                var style = oUl.style;
                style.display = style.display == "block" ? "none" : "block";
                oH2.className = style.display == "block" ? "open" : ""
            }
    }
});

// 文章角分类预览
Vue.component('article-list', {
    props:  ['articles', 'info'],
    template:   ' <div class="paihang">\n' +
    '                <h2 class="hometitle">{{info}}</h2>\n' +
    '                <ul v-for="ar in articles">\n' +
    '                    <li @click=showArticle(ar.id)>\n' +
    '                        <b>\n' +
    '                            <a v-bind:href="\'detail.html?id=\' + ar.id" target="_blank">{{ar.title}}</a>\n' +
    '                        </b>\n' +
    '                        <p>\n' +
    '                            <i>\n' +
    '                                <img v-bind:src="ar.img"/>\n' +
    '                            </i>\n' +
    '                            <p v-html="desc(ar.content)"></p>\n' +
    '                        </p>\n' +
    '                    </li>\n' +
    '                </ul>\n' +
    '            </div>'
});

// 主要文章列表
Vue.component('main-articles', {
    props: ['datas'],
    template: ' <ul>\n' +
    '                <li v-for="data in datas">\n' +
    '                    <h3 class="blogtitle">\n' +
    '                            <span>\n' +
    '                                <a v-bind:href="\'detail.html?id=\' + data.id" title="css3" target="_blank" class="classname">{{data.title}}</a>\n' +
    '                            </span>\n' +
    '                        <a v-bind:href="\'detail.html?id=\' + data.id" target="_blank"></a>\n' +
    '                    </h3>\n' +
    '                    <div class="bloginfo">\n' +
    '                            <span class="blogpic">\n' +
    '                                <a href="">\n' +
    '                                    <img v-bind:src="data.img"/>\n' +
    '                                </a>\n' +
    '                            </span>\n' +
    '                        <p v-html="desc(data.content)"></p>\n' +
    '                    </div>\n' +
    '                    <div class="autor">\n' +
    '                        <span class="lm f_l"></span>\n' +
    '                        <span class="dtime f_l">{{new Date(data.releasedate).Format("yyyy-MM-dd")}}</span>\n' +
    '                        <span class="viewnum f_l">浏览（<a href="/">{{data.clicks}}</a>）</span>\n' +
    '                        <span class="f_r">\n' +
    '                                <a v-bind:href="\'detail.html?id=\' + data.id" class="more">阅读原文>></a>\n' +
    '                            </span>\n' +
    '                    </div>\n' +
    '                    <div class="line"></div>\n' +
    '                </li>\n' +
    '            </ul>'
});


// 格式化时间日期 格式 yyyy-mm-dd.html dd.html::ss:tt 可以省略时间只保留日期
Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
        if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
};

// 得到请求参数
function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}

var id = getURLParameter('id');

var type = getURLParameter("type");

