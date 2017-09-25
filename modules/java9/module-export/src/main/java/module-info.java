/**
 *
 * User: luolibing
 * Date: 2017/9/22 18:38
 */
module cn.export {
    // 只导出给cn.require模块使用
    exports cn.tim.module.export to cn.require;

    requires java.base;

    uses java.util.ArrayList;
}