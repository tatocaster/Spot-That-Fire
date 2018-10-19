package me.tatocaster.nasaappchallenge.common.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import com.mikepenz.materialdrawer.model.AbstractDrawerItem
import kotlinx.android.synthetic.main.material_drawer_item_primary_centered.view.*
import me.tatocaster.nasaappchallenge.R


class CustomDrawerPrimaryItem : AbstractDrawerItem<CustomDrawerPrimaryItem, CustomDrawerPrimaryItem.ViewHolder>() {
    private var name: String? = null
    private var hiddenDivider: Boolean = false

    fun withName(name: String): CustomDrawerPrimaryItem {
        this.name = name
        return this
    }

    fun withHiddenDivider(): CustomDrawerPrimaryItem {
        this.hiddenDivider = true
        return this
    }

    override fun getLayoutRes(): Int {
        return R.layout.material_drawer_item_primary_centered
    }

    override fun getType(): Int {
        return R.id.material_drawer_item_custom_item
    }

    override fun bindView(holder: ViewHolder, payloads: List<Any>?) {
        super.bindView(holder, payloads)

        val ctx = holder.itemView.context


        holder.render(this.name, hiddenDivider)

        onPostBindView(this, holder.itemView)
    }

    override fun getViewHolder(v: View): ViewHolder {
        return ViewHolder(v)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun render(name: String?, hide: Boolean) {
            itemView.materialDrawerName.text = name
            if (hide)
                itemView.materialDrawerDivider.visibility = View.GONE
        }
    }

    companion object {
        fun newPrimaryItem(context: Context, id: Long, name: String): CustomDrawerPrimaryItem {
            return CustomDrawerPrimaryItem()
                    .withIdentifier(id)
                    .withName(name)
        }
    }
}
