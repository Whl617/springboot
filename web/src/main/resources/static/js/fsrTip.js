$(function () {
    $.fn.fsrTip = function (options) {
        var defaults = {
            Event: 'mouseover',                 //事件
            photo: "images/face.gif", //图片路径
            name: 'fsr',                        //姓名
            sex: '男',                           //性别
            love: '女',                          //爱好
            remark: "模仿中"                       //备注
        };
        var options = $.extend(defaults, options); //合并  将defaults 与 options合并
        //var bid = parseInt(Math.random() * 100000);
        $('body').prepend("<div id='user' class='user'><span></span><div class='out'><div class='in'><a href='#' title='查看资料'><img src='" + options.photo + "' alt='' /></a><div>名称：" + options.name + "<br />性别：" + options.sex + "<br />爱好：" + options.love + "<br />介绍：" + options.remark + "<br /><a href='#' class='btn btn-success btn-small' title='加关注'>+加关注</a>&nbsp;&nbsp;<a href='#' class='btn btn-success btn-small' title='加好友'>+加好友</a></div></div></div></div>");
        //响应事件
        var $this = $(this);
        var $btip = $("#user");
        var timer = null;
        $this.die().live(options.Event, function () {
            var offset = $(this).offset();
            debugger;
            var h = $this.height();
            var w = $this.width();
            if ($btip.height() > offset.top && $btip.width() > offset.left) {
                $btip.find("span").addClass('tl');
                $btip.css({
                    "left": offset.left + (w / 2),
                    "top": offset.top + h + 5
                }).show();
            }
            else if ($btip.height() > offset.top && ($btip.width() + offset.left + w) > document.body.clientWidth) {
                $btip.find("span").addClass('tr');
                $btip.css({
                    "left": offset.left - 2 * w,
                    "top": offset.top + h + 5
                }).show();
            }
            else if (($btip.height() + offset.top + h) > document.body.clientHeight && $btip.width() > offset.left) {
                $btip.find("span").addClass('bl');
                $btip.css({
                    "left": offset.left + (w / 2),
                    "top": offset.top - (13 * h - 5)
                }).show();
            }
            else if (($btip.height() + offset.top + h) > document.body.clientHeight && ($btip.width() + offset.left + w) > document.body.clientWidth) {
                $btip.find("span").addClass('br');
                $btip.css({
                    "left": offset.left - 2 * w,
                    "top": offset.top - (13 * h - 5)
                }).show();
            }
            else if ($btip.height() > offset.top || $btip.width() > offset.left) {
                $btip.find("span").addClass('tl');
                $btip.css({
                    "left": offset.left + (w / 2),
                    "top": offset.top + h + 5
                }).show();
            }
            else {
                $btip.find("span").addClass('br');
                $btip.css({
                    "left": offset.left - 2 * w,
                    "top": offset.top - (13 * h - 5)
                }).show();
            }
        }).live('mouseout', function () {
            timer = setTimeout(function () {
                $btip.hide();
                clearTimeout(timer);
            }, 1000)
        });
        $btip.die().live(options.Event, function () {
            clearTimeout(timer);
            $(this).show();
        }).live('mouseout', function () {
            clearTimeout(timer);
            $(this).hide();
        });
    }
});