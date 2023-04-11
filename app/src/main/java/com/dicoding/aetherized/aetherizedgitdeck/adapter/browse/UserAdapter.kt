package com.dicoding.aetherized.aetherizedgitdeck.adapter.browse

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.aetherized.aetherizedgitdeck.R
import com.dicoding.aetherized.aetherizedgitdeck.data.User
import com.bumptech.glide.Glide
import com.dicoding.aetherized.aetherizedgitdeck.data.Constants
import com.dicoding.aetherized.aetherizedgitdeck.ui.details.fragments.DetailsFragment
import cz.msebera.android.httpclient.client.methods.RequestBuilder.put

class UserAdapter(private var userList: List<User>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {


    fun updateList(users: List<User>) {
        val diffResult = DiffUtil.calculateDiff(UserDiffCallback(userList, users))
        userList = users
        diffResult.dispatchUpdatesTo(this)
    }
    fun getList(): List<User> {
        return userList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.linear_layout_list_item, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val user = userList[position]

        viewHolder.bind(user)

    }

    override fun getItemCount(): Int = userList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val userAvatar: ImageView = itemView.findViewById(R.id.user_avatar_iv)
        private val username: TextView = itemView.findViewById(R.id.username_tv)
        private val userId: TextView = itemView.findViewById(R.id.userid_tv)

        fun bind(user: User) {
            Glide.with(itemView).load(user.avatarUrl).into(userAvatar)
            username.text = user.username
            userId.text = user.id.toString()
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

    class UserDiffCallback(private val oldList: List<User>, private val newList: List<User>) :
        DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldUser = oldList[oldItemPosition]
            val newUser = newList[newItemPosition]
            return oldUser.id == newUser.id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldUser = oldList[oldItemPosition]
            val newUser = newList[newItemPosition]
            return oldUser == newUser
        }
    }
}