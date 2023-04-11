package com.dicoding.aetherized.aetherizedgitdeck.adapter.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.data.Constants
import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.dicoding.aetherized.aetherizedgitdeck.helper.FavoriteDiffCallback
import com.dicoding.aetherized.aetherizedgitdeck.ui.details.fragments.DetailsFragment

class FavoritesAdapter(private var user: ArrayList<User>) :
    RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {


    fun setFavorites(favorites: List<User>) {
        val diffCallback = FavoriteDiffCallback(this.user, favorites)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.user.clear()
        this.user.addAll(favorites)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.grid_layout_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val favorite = user[position]

        viewHolder.bind(favorite)
    }

    override fun getItemCount(): Int = user.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userAvatar: ImageView = itemView.findViewById(R.id.user_avatar_iv)
        private val username: TextView = itemView.findViewById(R.id.username_tv)
        private val name: TextView = itemView.findViewById(R.id.name_tv)

        fun bind(user: User) {
            Glide.with(itemView).load(user.avatarUrl).into(userAvatar)
            username.text = user.username
            name.text = user.username
            itemView.setOnClickListener {
                val bundle = Bundle().apply {
                    putParcelable(Constants.PARCELABLE_KEY, user)
                }

                val detailsFragment = DetailsFragment().apply {
                    arguments = bundle
                }

                val transaction = (itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, detailsFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        }
    }
}