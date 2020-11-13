package com.example.randomuserapp.fragments

import android.os.Bundle
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.randomuserapp.R
import com.example.randomuserapp.databinding.UserFragmentBinding
import com.example.randomuserapp.view_model.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class UserFragment : Fragment() {

    private val args: UserFragmentArgs by navArgs()

    private val viewModel: UserViewModel by viewModel { parametersOf(args.user)}

    private lateinit var binding: UserFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.userLiveData.observe(viewLifecycleOwner) { user ->

            val resPlaceholder = if (user.gender == "male") R.drawable.man else R.drawable.woman

            Glide.with(this)
                .load(user.picture.large)
                .error(resPlaceholder)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(binding.imageAvatar)

            with(binding) {
                textFullName.text = resources.getString(
                    R.string.full_name_age,
                    user.name.fullName,
                    user.dob.age
                )
                textEmail.text = user.email
                textPhone.text = user.phone
                textPhoneCell.text = user.cell
                textCountry.text = resources.getString(R.string.country, user.location.country)
                textState.text = resources.getString(R.string.state, user.location.state)
                textCity.text = resources.getString(R.string.city, user.location.city)
                textStreet.text = resources.getString(
                    R.string.street,
                    user.location.street.fullStreetName
                )
                textPostcode.text = resources.getString(R.string.postcode, user.location.postcode)
                textTimeZone.text = resources.getString(
                    R.string.timezone,
                    user.location.timezone.fullDescription
                )
            }
        }
    }

}