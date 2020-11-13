package com.example.randomuserapp

import User
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.randomuserapp.databinding.ItemUserBinding

class UserListAdapter(
    private var users: List<User>,
    private val listener: ((user: User) -> Unit)?
):
    RecyclerView.Adapter<UserListAdapter.UserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val itemBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun setList(newUsers: List<User>) {
        users = newUsers
        notifyDataSetChanged()
    }


    inner class UserHolder(
        private val binding: ItemUserBinding
    ): RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            val resPlaceholder = if (user.gender == "male") R.drawable.man else R.drawable.woman

            Glide.with(binding.root)
                .load(user.picture.medium)
                .placeholder(resPlaceholder)
                .error(resPlaceholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageView)

            binding.textFullName.text = user.name.fullName
            binding.textEmail.text = user.email

            binding.root.setOnClickListener {
                if (adapterPosition == RecyclerView.NO_POSITION) return@setOnClickListener
                listener?.invoke(users[adapterPosition])
            }
        }
    }
}