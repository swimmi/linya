package net.swimmi.linya.data

import net.swimmi.linya.R

class DatConst {

    companion object {

        var PARTNER_TYPES: Array<String> = arrayOf("全部", "磐石", "玄铁", "流光", "飞羽", "仙草", "朔风")
        var PARTNER_TYPE_ICONS: Array<Int> = arrayOf(
                R.drawable.ic_partner_circle,
                R.drawable.ic_partner_square,
                R.drawable.ic_partner_triangle,
                R.drawable.ic_partner_star,
                R.drawable.ic_partner_diamond,
                R.drawable.ic_partner_heart,
                R.drawable.ic_partner_cloud)
        var PARTNER_TYPE_ON_ICONS: Array<Int> = arrayOf(
                R.drawable.ic_partner_circle_on,
                R.drawable.ic_partner_square_on,
                R.drawable.ic_partner_triangle_on,
                R.drawable.ic_partner_star_on,
                R.drawable.ic_partner_diamond_on,
                R.drawable.ic_partner_heart_on,
                R.drawable.ic_partner_cloud_on)

        var PARTNER_LINGS: Array<String> = arrayOf("全", "火", "水", "木", "光", "暗")
        var PARTNER_LING_ICONS: Array<Int> = arrayOf(
                R.drawable.bg_null,
                R.drawable.bg_fire,
                R.drawable.bg_water,
                R.drawable.bg_wood,
                R.drawable.bg_light,
                R.drawable.bg_dark
        )

        var RARE_STONE_TYPES = arrayOf("飞仙", "格挡", "坚固")
        var STONE_TYPES = arrayOf("磐牛", "猛虎", "玄龟", "暴熊", "丹鹤", "迅狐", "蟠龙", "灵犀", "回春", "勇武")
        var STONE_RATES = arrayOf(arrayOf(60, 40), arrayOf(60, 30, 10), arrayOf(40, 40, 20), arrayOf(30, 30, 30, 10), arrayOf(0, 0, 0, 60, 10, 30))
        var RANDOM_NAMES = arrayOf("琉璃", "修齐", "黑图", "金一鉴", "白须长老")
        var RANDOM_RATES = arrayOf(50, 50, 50, 50, 100)
        var RANDOM_COLOURS = arrayOf(R.drawable.bg_grey_btn, R.drawable.bg_green_btn, R.drawable.bg_blue_btn, R.drawable.bg_purple_btn, R.drawable.bg_orange_btn)
        var RANDOM_COSTS = arrayOf(6000, 8000, 10000, 20000, 50000)
    }
}