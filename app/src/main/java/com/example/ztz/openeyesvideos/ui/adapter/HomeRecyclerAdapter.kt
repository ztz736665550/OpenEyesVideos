package com.example.ztz.openeyesvideos.ui.adapter

import android.app.SearchManager
import android.content.Context
import android.opengl.Visibility
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.example.ztz.openeyesvideos.R
import com.example.ztz.openeyesvideos.base.BaseRecyclerViewAdapter
import com.example.ztz.openeyesvideos.bean.HomeBean
import com.example.ztz.openeyesvideos.utils.ToastUtil
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader


/**
 * Created by ztz on 2018/7/26.
 */
class HomeRecyclerAdapter(private var context: Context, private var mBannerList : ArrayList<String>,private var mBannerTitle : ArrayList<String>,private var data: ArrayList<HomeBean.Issue.Item>)
    : BaseRecyclerViewAdapter<RecyclerView.ViewHolder>() {

    private val ITEM_BANNER_TYPE : Int = 1
    private val ITEM_DATE_TYPE : Int = 2
    private val ITEM_VIDEO_TYPE : Int = 3

    override fun getItemViewType(position: Int): Int {

        return when{
            position == 0 -> return ITEM_BANNER_TYPE
            data[position].data.type.equals("textHeader") -> return ITEM_DATE_TYPE
            else -> {
                return ITEM_VIDEO_TYPE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when{
            viewType == ITEM_BANNER_TYPE -> return mBannerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_homeadapter_banner,null))
            viewType == ITEM_DATE_TYPE -> return mDateViewHolder(LayoutInflater.from(context).inflate(R.layout.item_homeadapter_date,null))
            else ->{
                return mVideoViewHolder(LayoutInflater.from(context).inflate(R.layout.item_homeadapter_video,null))
            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(vh: RecyclerView.ViewHolder, i: Int) {
        super.onBindViewHolder(vh, i)
        if (vh is mBannerViewHolder){
            vh.mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
            vh.mBanner.setImages(mBannerList)
            vh.mBanner.setBannerTitles(mBannerTitle)
            vh.mBanner.setImageLoader(mBannerImageLoader())
            vh.mBanner.start()
        }else if (vh is mDateViewHolder){
            vh.mDateTv.text = data[i].data.title
        }else if (vh is mVideoViewHolder){
            Glide.with(context).load(data[i].data.cover.detail).into(vh.mVideoIv);
            Glide.with(context).load(data[i].data.provider.icon).apply(RequestOptions.bitmapTransform(CircleCrop())).into(vh.mHeaderIv);
            vh.mTitleTv.text = data[i].data.title
            vh.mDescribeTv.text = data[i].data.category
            vh.mTagTv.text = data[i].data.type
//            vh.mHeartBrokenIv.setOnClickListener {
//                vh.mHeartBrokenIv.setImageResource(R.mipmap.ic_heart_broken_black)
//                vh.mHeartBrokenIv.visibility = View.GONE
//            }
//            vh.mVideoIv.setOnLongClickListener {
//                vh.mHeartBrokenIv.visibility = View.VISIBLE
//            }
        }
    }


    /**
     * banner
     */
    class mBannerViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        lateinit var mBanner : Banner
        init {
            mBanner = itemView.findViewById(R.id.mBanner)
        }
    }

    /**
     * date
     */
    class mDateViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        lateinit var mDateTv : TextView
        init {
            mDateTv = itemView.findViewById(R.id.mDateTv)
        }
    }

    /**
     * video
     */
    class mVideoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        lateinit var mVideoIv : ImageView
        lateinit var mHeaderIv : ImageView
        lateinit var mTitleTv : TextView
        lateinit var mDescribeTv : TextView
        lateinit var mTagTv : TextView
        lateinit var mIndifferentRl : RelativeLayout
        lateinit var mHeartBrokenIv : ImageView
        init {
            mVideoIv = itemView.findViewById(R.id.mVideoIv)
            mHeaderIv = itemView.findViewById(R.id.mHeaderIv)
            mTitleTv = itemView.findViewById(R.id.mTitleTv)
            mDescribeTv = itemView.findViewById(R.id.mDescribeTv)
            mTagTv = itemView.findViewById(R.id.mTagTv)
            mIndifferentRl = itemView.findViewById(R.id.mIndifferentRl)
            mHeartBrokenIv = itemView.findViewById(R.id.mHeartBrokenIv)
        }
    }

    /**
     * banner、图片加载器
     */
    class mBannerImageLoader : ImageLoader(){
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context).load(path).into(imageView);
        }
    }
}