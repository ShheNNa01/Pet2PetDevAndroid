package com.example.frontpet2pet.ui.auth.register;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.frontpet2pet.R;
import com.example.frontpet2pet.api.ApiClient;
import com.example.frontpet2pet.data.models.request.Breeds;
import com.example.frontpet2pet.data.models.request.RegisterPetRequest;
import com.example.frontpet2pet.data.models.response.PetResponse;
import com.example.frontpet2pet.ui.profile.ZonaPetFragment;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterPet extends Fragment {

    private EditText edtName, edtBirthDate, edtBiog;
    private Spinner spinnerBreed, spinnerGender; // Spinner para Raza y Género
    private Button buttonCrear;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register_pet, container, false);

        // Inicializar vistas
        edtName = view.findViewById(R.id.edtName);
        edtBirthDate = view.findViewById(R.id.edtBirthDate);
        edtBiog = view.findViewById(R.id.edtBiog);
        spinnerBreed = view.findViewById(R.id.spinnerBreed);
        spinnerGender = view.findViewById(R.id.spinnerGender);  // Spinner para Género
        buttonCrear = view.findViewById(R.id.buttonCrear);

        // Cargar razas
        loadPetBreeds();

        // Configurar el botón de registro
        buttonCrear.setOnClickListener(v -> registerPet());

        return view;
    }

    private void loadPetBreeds() {
        ApiClient.getInstance().getApiService().getPetBreeds(0 , 100).enqueue(new Callback<List<Breeds>>() {
            @Override
            public void onResponse(Call<List<Breeds>> call, Response<List<Breeds>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Obtener la lista de razas
                    List<Breeds> breedsList = response.body();

                    // Crear el ArrayAdapter con los nombres de las razas
                    ArrayAdapter<Breeds> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, breedsList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    // Configurar el Spinner con el ArrayAdapter
                    spinnerBreed.setAdapter(adapter);
                } else {
                    Toast.makeText(getContext(), "Error al cargar las razas", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Breeds>> call, Throwable t) {
                Toast.makeText(getContext(), "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void registerPet() {
        String name = edtName.getText().toString().trim();
        Breeds selectedBreed = (Breeds) spinnerBreed.getSelectedItem(); // Obtener el objeto Breeds seleccionado
        String breed = selectedBreed != null ? selectedBreed.getBreedName() : ""; // Obtener el nombre de la raza
        int breedId = selectedBreed != null ? selectedBreed.getBreedId() : -1; // Obtener el ID de la raza
        String gender = spinnerGender.getSelectedItem().toString(); // Obtener el género seleccionado
        String birthdate = edtBirthDate.getText().toString().trim();
        String biography = edtBiog.getText().toString().trim();

        //Validacion para genero en español la muestra, pero por debajo en ingles
        if (gender.equals("Masculino")) {
            gender = "male";
        } else if (gender.equals("Femenino")) {
            gender = "female";
        }

        // Validaciones
        if (name.isEmpty()) {
            Toast.makeText(getContext(), "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
            return;
        }
        if (breedId == -1) {
            Toast.makeText(getContext(), "Selecciona una raza válida", Toast.LENGTH_SHORT).show();
            return;
        }
        if (gender.equals("Selecciona un género")) {
            Toast.makeText(getContext(), "Selecciona un género válido", Toast.LENGTH_SHORT).show();
            return;
        }
        if (birthdate.isEmpty()) {
            Toast.makeText(getContext(), "La fecha de nacimiento es obligatoria", Toast.LENGTH_SHORT).show();
            return;
        }
        if (biography.isEmpty()) {
            Toast.makeText(getContext(), "La biografía es obligatoria", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear el objeto RegisterPetRequest con el género seleccionado
        RegisterPetRequest request = new RegisterPetRequest(name, breed, gender, birthdate, biography);

        // Llamar al endpoint para registrar la mascota
        ApiClient.getInstance().getApiService().createPet(request).enqueue(new Callback<PetResponse>() {
            @Override
            public void onResponse(Call<PetResponse> call, Response<PetResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Mascota registrada exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PetResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectToZonePetFragment() {
        // Redirigir a fragment_zonepet
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, new ZonaPetFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
