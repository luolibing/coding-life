package com.tim.springboot.source.summary;

/**
 * 1 autoConfiguration提供一些可选的配置，同时为了保证在autoConfig中可编译使用，
 *      同时又不想把所有的依赖都循环加入到使用autoconfig的项目中。在dependency中使用了<optional>true</optional>将导入的配置只在当前autoConfig有效。
 *      autoConfig中的autoConfig类
 *
 * User: luolibing
 * Date: 2017/9/1 16:45
 */
public class SummaryService {
}
