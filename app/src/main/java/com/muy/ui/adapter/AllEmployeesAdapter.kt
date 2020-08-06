package com.muy.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.core.animation.doOnEnd
import androidx.core.animation.doOnStart
import androidx.core.view.doOnLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.muy.R
import com.muy.data.dto.EmployeesItem
import com.muy.data.dto.News
import com.muy.util.*
import kotlinx.android.synthetic.main.row_employee.view.*
import java.util.*
import kotlin.collections.ArrayList


open class AllEmployeesAdapter(
    private val listener: (id: Int) -> Unit,
    private val context: Context
) :RecyclerView.Adapter<AllEmployeesAdapter.ListViewHolder>(){

    private var museums: List<EmployeesItem>  = emptyList()
    private var cmuseums: List<EmployeesItem> = emptyList()
    private var news: List<News> = emptyList()

    private lateinit var recyclerView: RecyclerView

    private val originalBg: Int by bindColor(context, R.color.list_item_bg_collapsed)
    private val expandedBg: Int by bindColor(context, R.color.list_item_bg_expanded)
    private val listItemHorizontalPadding: Float by bindDimen(context, R.dimen.list_item_horizontal_padding)
    private val listItemVerticalPadding: Float by bindDimen(context, R.dimen.list_item_vertical_padding)
    private val originalWidth = context.screenWidth - 48.dp
    private val expandedWidth = context.screenWidth - 24.dp
    private var originalHeight = -1
    private var expandedHeight = -1
    private var isScaledDown = false
    var animationPlaybackSpeed: Double = 0.5
    private val listItemExpandDuration: Long get() = (300L / animationPlaybackSpeed).toLong()
    private var expandedModel: EmployeesItem? = null


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        super.onAttachedToRecyclerView(recyclerView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_employee, parent, false)
        return ListViewHolder(view)
    }


    override fun onBindViewHolder(vh: ListViewHolder, position: Int) {
        val model = museums[position]
        expandItem(vh, model == expandedModel, animate = false)
        scaleDownItem(vh, isScaledDown)



        vh.cardContainer.setOnClickListener {
            if (expandedModel == null) {

                // expand clicked view
                expandItem(vh, expand = true, animate = true)
                expandedModel = model
            } else if (expandedModel == model) {

                // collapse clicked view
                expandItem(vh, expand = false, animate = true)
                expandedModel = null
            } else {

                // collapse previously expanded view
                val expandedModelPosition = museums.indexOf(expandedModel!!)
                val oldViewHolder =
                    recyclerView.findViewHolderForAdapterPosition(expandedModelPosition) as? ListViewHolder
                if (oldViewHolder != null) expandItem(oldViewHolder, expand = false, animate = true)

                // expand vh view
                expandItem(vh, expand = true, animate = true)
                expandedModel = model
            }
        }

        vh.newBtn.setOnClickListener {listener.invoke(model.id!!)}

        vh.bind(model,news,context)
    }

    override fun getItemCount(): Int {
        return museums.size
    }



    fun updateNews(data:List<News>){
        news= data

        notifyDataSetChanged()
    }

    fun update(data:List<EmployeesItem>){
        museums= data
        cmuseums = data
         notifyDataSetChanged()
    }


    fun search(text: String) {
        museums =cmuseums
        var charText = text.toLowerCase(Locale.getDefault())
        var data = ArrayList<EmployeesItem>()
        if (charText.isEmpty()) {
            data.addAll(museums)
        } else {
            for (wp in museums) {
                if (wp.name!!.toLowerCase(Locale.getDefault()).contains(charText)) {
                    data.add(wp)
                }
            }
        }
        museums= data
        notifyDataSetChanged()
    }

    fun filter() {

        var sort = ArrayList<EmployeesItem>()
        var temp :EmployeesItem?
        sort.addAll(museums)

        for (i in sort.indices) {
            for (j in 0 until sort.size - 1) {
                if (sort[j + 1].wage!!.compareTo(sort[j].wage!!) <0) {
                    temp = sort[j + 1]
                    sort[j + 1] = sort[j]
                    sort[j] = temp
                }
            }
        }
        museums = sort
        notifyDataSetChanged()
    }

    open class ListViewHolder(view: View) : RecyclerView.ViewHolder(view){
        private val textViewName:TextView = view.title
        val listItemFg:View = view.list_item_fg
        private val imagenew: ImageView = view.imagenew
        val position: TextView = view.position
        private val wage:TextView = view.wage
        val scaleContainer:View = view.scale_container
        val cardContainer: View = view.card_container
        val newBtn: View = view.newbtn
        val expandView :RecyclerView = view.expand_view
        val chevron :View = view.chevron
        fun bind(
            employee: EmployeesItem,
            news: List<News>,
            context: Context
        ){
            textViewName.text = employee.name
            wage.text =employee.wage.toString()
            position.text = employee.position
            expandView.layoutManager = LinearLayoutManager(context)
            imagenew.setImageResource(android.R.drawable.btn_star_big_off)

            news.forEach{

                if(it.id == employee.id){
                    imagenew.setImageResource(android.R.drawable.btn_star_big_on)
                    return
                }
            }
            var adapter = SubEmployeeAdapter(employee.employees)
            expandView.adapter = adapter
        }
    }


    private fun setScaleDownProgress(holder: ListViewHolder, progress: Float) {

        holder.cardContainer.requestLayout()

        holder.scaleContainer.scaleX = 1 - 0.05f * progress
        holder.scaleContainer.scaleY = 1 - 0.05f * progress

        holder.scaleContainer.setPadding(
            (listItemHorizontalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemVerticalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemHorizontalPadding * (1 - 0.2f * progress)).toInt(),
            (listItemVerticalPadding * (1 - 0.2f * progress)).toInt()
        )

        holder.listItemFg.alpha = progress
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }

    private fun expandItem(holder: ListViewHolder, expand: Boolean, animate: Boolean) {
        if (animate) {
            val animator = getValueAnimator(
                expand, listItemExpandDuration, AccelerateDecelerateInterpolator()
            ) { progress -> setExpandProgress(holder, progress) }

            if (expand) animator.doOnStart {
                holder.expandView.isVisible = true
            }
            else animator.doOnEnd {
                holder.expandView.isVisible = false
            }

            animator.start()
        } else {

            // show expandView only if we have expandedHeight (onViewAttached)
            holder.expandView.isVisible = expand && expandedHeight >= 0
            setExpandProgress(holder, if (expand) 1f else 0f)
        }
    }

    private fun setExpandProgress(holder: ListViewHolder, progress: Float) {
        if (expandedHeight > 0 && originalHeight > 0) {
            holder.cardContainer.layoutParams.height =
                (originalHeight + (expandedHeight - originalHeight) * progress).toInt()
        }
        holder.cardContainer.layoutParams.width =
            (originalWidth + (expandedWidth - originalWidth) * progress).toInt()

        holder.cardContainer.setBackgroundColor(blendColors(originalBg, expandedBg, progress))

        // this have a blink in the recyclerview
        holder.cardContainer.requestLayout()

        holder.chevron.rotation = 90 * progress
    }


    override fun onViewAttachedToWindow(holder: ListViewHolder) {
        super.onViewAttachedToWindow(holder)

        if (expandedHeight < 0) {
            expandedHeight = 0

            holder.cardContainer.doOnLayout { view ->
                originalHeight = view.height

                holder.expandView.isVisible = true
                view.doOnPreDraw {
                    expandedHeight = view.height
                    holder.expandView.isVisible = false
                }
            }
        }
    }

    private fun scaleDownItem(holder: ListViewHolder, isScaleDown: Boolean) {
        setScaleDownProgress(holder, if (isScaleDown) 1f else 0f)
    }

}