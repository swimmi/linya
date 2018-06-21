package net.swimmi.linya.ui.view.combine

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import kotlinx.android.synthetic.main.partner_view.view.*
import net.swimmi.linya.R
import net.swimmi.linya.data.DatConst
import net.swimmi.linya.model.MyPartner
import net.swimmi.linya.model.Partner
import net.swimmi.linya.ui.utils.UDisplay


class PartnerView(context: Context, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    init {
        LayoutInflater.from(context).inflate(R.layout.partner_view, this)
        if(attrs != null) {
            val ta: TypedArray = context.obtainStyledAttributes(attrs, R.styleable.PartnerView)
            for (i in 0..ta.indexCount) {
                val itemId: Int = ta.getIndex(i)
                when (itemId) {
                    R.styleable.PartnerView_name -> tv_name.text = ta.getText(itemId)
                    R.styleable.PartnerView_level -> setLevel(ta.getInt(itemId, 1))
                    R.styleable.PartnerView_isBoss -> tv_boss.visibility = if (ta.getBoolean(itemId, false)) View.VISIBLE else View.GONE
                    R.styleable.PartnerView_hasDot -> v_dot.visibility = if (ta.getBoolean(itemId, false)) View.VISIBLE else View.GONE
                    R.styleable.PartnerView_ling -> v_ling.setBackgroundResource(DatConst.PARTNER_LING_ICONS[ta.getInteger(itemId, 0)])
                    R.styleable.PartnerView_starCount -> setStar(ta.getInt(itemId, 0))
                }
            }
            ta.recycle()
        }
    }

    private fun setLevel(level: Int) {
        tv_level.visibility = View.VISIBLE
        tv_level.text = level.toString()
    }

    private fun setStar(star: Int) {
        var starCount = star
        var resId = R.mipmap.partner_star
        if (starCount > 5) {
            starCount -= 5
            resId = R.mipmap.partner_star_purple
        }
        ll_star.removeAllViews()
        (1..starCount).forEach {
            val starIv = ImageView(context)
            val size = UDisplay(context).d2p(9f)
            val layoutParams = LinearLayout.LayoutParams(size, size)
            starIv.layoutParams = layoutParams
            starIv.setImageResource(resId)
            ll_star.addView(starIv)
        }
    }

    fun setValues(partner: Partner) {
        tv_name.text = partner.name
        v_ling.setBackgroundResource(DatConst.PARTNER_LING_ICONS[partner.ling])

    }

    fun setValues(myPartner: MyPartner) {
        val partner = myPartner.partnerItem
        setValues(partner!!)
        setStar(myPartner.star)
        setLevel(myPartner.level)
    }

}