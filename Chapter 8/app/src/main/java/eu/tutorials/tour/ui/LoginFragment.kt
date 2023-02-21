package eu.tutorials.tour.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import eu.tutorials.tour.R
import eu.tutorials.tour.data.FirebaseServiceImpl
import eu.tutorials.tour.databinding.FragmentLoginBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val firebaseServices by lazy {
        FirebaseServiceImpl()
    }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).title = getString(R.string.login)
         if (firebaseServices.hasUser)
             findNavController().navigate(R.id.action_LoginFragment_to_TourFragment)
        setUpViewActions();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

  private fun setUpViewActions(){
      binding.apply {
          loginBtn.setOnClickListener {
              val emailEditText = binding.emailEt.text.toString()
              val passwordEditText = binding.passwordEt.text.toString()
              if (emailEditText.isNullOrEmpty() || passwordEditText.isNullOrEmpty()) {
                  Toast.makeText(
                      requireContext(),
                      "Fields cannot be empty",
                      Toast.LENGTH_LONG
                  ).show()
              } else {
                  firebaseServices.SignInUser(
                      emailEditText,
                      passwordEditText
                  ) {
                      if (it.isSuccessful) {
                          if (findNavController().currentDestination?.id != R.id.action_LoginFragment_to_TourFragment){
                              findNavController().navigate(R.id.action_LoginFragment_to_TourFragment)
                          }
                     } else {
                          Toast.makeText(
                              requireContext(),
                              it.exception.toString(),
                              Toast.LENGTH_LONG
                          ).show()
                      }
                  }
              }
          }
          signUpBtn.setOnClickListener {
              val emailEditText = binding.emailEt.text.toString()
              val passwordEditText = binding.passwordEt.text.toString()
              if (emailEditText.isNullOrEmpty() || passwordEditText.isNullOrEmpty()) {
                  Toast.makeText(
                      requireContext(),
                      "Fields cannot be empty",
                      Toast.LENGTH_LONG
                  ).show()
              } else {
                  firebaseServices.registerUser(
                      emailEditText,
                      passwordEditText
                  ) {
                      if (it.isSuccessful) {
                          Toast.makeText(
                              requireContext(),
                              "User Registered", Toast.LENGTH_LONG
                          ).show()
                          if (findNavController().currentDestination?.id != R.id.action_LoginFragment_to_TourFragment){
                              findNavController().navigate(R.id.action_LoginFragment_to_TourFragment)
                          }
                          Log.d("LoginActivity", it.result.user?.email.toString())
                      } else {
                          Toast.makeText(
                              requireContext(),
                              it.exception.toString(), Toast.LENGTH_LONG
                          ).show()
                          Log.d("LoginActivity", it.exception.toString())
                      }
                  }
              }
          }
          //Hide Signup views and show login views
          loginInstruction.setOnClickListener {
              loginHideGroup.visibility = View.GONE
              signUpHideGroup.visibility = View.VISIBLE
              (activity as MainActivity).title = getString(R.string.login)
          }
          //Hide Login views and show Signup views
          signUpInstruction.setOnClickListener {
              loginHideGroup.visibility = View.VISIBLE
              signUpHideGroup.visibility = View.GONE
              (activity as MainActivity).title = getString(R.string.signUp)
          }
      }
  }
}