package com.example.androidapplication

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.androidapplication.api.UserAPIService
import com.example.androidapplication.databinding.FragmentFirstBinding
import com.example.androidapplication.model.User
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userAPIService = UserAPIService.create()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener{
            val editText = binding.edittextId.editableText
            val user = userAPIService.getUser(editText.toString());
            Log.i("FirstFragment","onViewCreated")
            user.enqueue(object:retrofit2.Callback<User>{

                override fun onResponse(call:Call<User>,response: Response<User>){
                    val body = response.body()
                    body?.let {
                        Log.i("FirstFragment", it.name)
                        binding.textviewThird.text = it.name
                        binding.textviewFourth.text = it.email
                    }

                }

                override fun onFailure(call:Call<User>,t:Throwable){
                    Log.i("FirstFragment","Error")
                }

            })

        }


        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}