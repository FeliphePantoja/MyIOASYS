package br.com.myapplication.view;

import android.os.Bundle;

import androidx.databinding.library.baseAdapters.BR;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import br.com.myapplication.R;
import br.com.myapplication.databinding.FragmentLoginBinding;
import br.com.myapplication.model.User;
import br.com.myapplication.viewmodel.LoginViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding fragmentLoginBinding;
    private LoginViewModel loginViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.fragmentLoginBinding = FragmentLoginBinding.inflate(inflater, container, false);
        this.fragmentLoginBinding.setLifecycleOwner(this);
        this.fragmentLoginBinding.setClickLogin(this::onClick);
        // Incializa variáveis
        this.loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        loadObservers();

        return fragmentLoginBinding.getRoot();
    }

    public void onClick(View v) {
        this.fragmentLoginBinding.setVariable(BR.setProgressLOGIN,true);
        this.loginViewModel.Login(this.fragmentLoginBinding.editTextUserEmail.getText().toString(),
                this.fragmentLoginBinding.editTextPassword.getText().toString());
    }

    private void loadObservers() {
        this.loginViewModel.login.observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.isEmpty()) {
                    NavDirections navDirections =LoginFragmentDirections.actionLoginFragmentToProdutoListFragment();
                    Navigation.findNavController(fragmentLoginBinding.getRoot()).navigate(navDirections);
                } else {
                    Toast.makeText(getActivity(), s, Toast.LENGTH_SHORT).show();
                }
                fragmentLoginBinding.setVariable(BR.setProgressLOGIN,false);
            }
        });
    }
}