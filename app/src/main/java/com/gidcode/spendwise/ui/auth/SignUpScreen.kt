package com.gidcode.spendwise.ui.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gidcode.spendwise.R
import com.gidcode.spendwise.domain.model.CreateAccountDomainModel
import com.gidcode.spendwise.ui.common.ErrorViewWithoutButton
import com.gidcode.spendwise.ui.common.PreviewContent
import com.gidcode.spendwise.ui.common.ViewModelProvider
import com.gidcode.spendwise.ui.navigation.Destination
import com.gidcode.spendwise.ui.navigation.Navigator

@Composable
fun SignUpScreen() {
   val authViewModel = ViewModelProvider.authToken
   val uiState by authViewModel.uiState.collectAsState()
   val uiEvent: (UIEvents) -> Unit = authViewModel::handleEvent
   SignUpScreenContent(
      uiState,
      uiEvent
   )
}

@Composable
fun SignUpScreenContent(
   uiState: UIState,
   events: (UIEvents) -> Unit
){
   val navController = Navigator.current
   var email by remember { mutableStateOf("") }
   var name by remember { mutableStateOf("") }
   var password by remember { mutableStateOf("") }
   var passwordVisible by remember { mutableStateOf(false) }

   var confirmPassword by remember { mutableStateOf("") }
   var confirmPasswordVisible by remember { mutableStateOf(false) }

   var showError by remember { mutableStateOf(false) }

   val isPasswordConfirmed by remember {
      derivedStateOf {
         password.lowercase() == confirmPassword.lowercase()
      }
   }

   val isFormValid by remember { derivedStateOf {
      email.isNotBlank() && password.isNotBlank()
   } }

   LaunchedEffect(key1 = uiState) {
      if (uiState.hasAuthToken) {
         navController.navigate(Destination.Main.route) {
            popUpTo(Destination.Authentication.route) { inclusive = true }
         }
      }else if (uiState.error != null){
         showError = true
      }
   }

   Surface(
      modifier = Modifier.fillMaxSize()
   ) {
      Box {
         Column(
            modifier = Modifier.padding(
               vertical = 10.dp,
               horizontal = 16.dp
            ),
            verticalArrangement = Arrangement.Top
         ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
               text = stringResource(id = R.string.signup),
               style = MaterialTheme.typography.displayMedium.copy(
                  fontSize = 36.sp,
                  fontWeight = FontWeight.Bold
               ),
               textAlign = TextAlign.Start
            )

            Text(
               text = stringResource(R.string.create_an_account_to_continue),
               style = MaterialTheme.typography.bodyMedium.copy(
                  fontSize = 12.sp,
                  fontWeight = FontWeight.Medium,
                  color = MaterialTheme.colorScheme.onSurfaceVariant
               ),
               textAlign = TextAlign.Start
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
               value = name,
               onValueChange = { name = it },
               label = { Text(stringResource(R.string.full_name)) },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
               enabled = !uiState.isLoading,
               modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
               value = email,
               onValueChange = { email = it },
               label = { Text(stringResource(R.string.email)) },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
               enabled = !uiState.isLoading,
               modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
               value = password,
               onValueChange = { password = it },
               label = { Text(stringResource(R.string.password)) },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
               visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
               trailingIcon = {
                  val icon =
                     if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                  IconButton(onClick = { passwordVisible = !passwordVisible }) {
                     Icon(imageVector = icon, contentDescription = stringResource(R.string.toggle_password_visibility))
                  }
               },
               enabled = !uiState.isLoading,
               modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
               value = confirmPassword,
               onValueChange = { confirmPassword = it },
               label = { Text(stringResource(R.string.confirm_password)) },
               keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
               visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
               trailingIcon = {
                  val icon =
                     if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                  IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                     Icon(imageVector = icon, contentDescription = stringResource(R.string.toggle_confirm_password_visibility))
                  }
               },
               enabled = !uiState.isLoading,
               modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Login Button
            Button(
               onClick = {
                  if (isPasswordConfirmed && isFormValid)
                     events.invoke(UIEvents.CreateUser(
                        CreateAccountDomainModel(
                           name,
                           email,
                           password
                        )
                     ))
               },
               enabled = !uiState.isLoading,
               modifier = Modifier.fillMaxWidth(),
               shape = RoundedCornerShape(8.dp)
            ) {
               if (uiState.isLoading) {
                  CircularProgressIndicator(
                     color = MaterialTheme.colorScheme.onPrimary
                  )
               } else {
                  Text(
                     stringResource(R.string.register),
                     style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                     )
                  )
               }
            }

            Spacer(modifier = Modifier.weight(1f))

            Text(
               modifier = Modifier
                  .align(Alignment.CenterHorizontally)
                  .clickable {
                     navController.navigate(Destination.Login.route)
                  },
               text = buildAnnotatedString {
                  withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.onSurface)) {
                     append(stringResource(R.string.already_have_an_account))
                  }
                  withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                     append(stringResource(R.string.login))
                  }
               },
               style = MaterialTheme.typography.bodyMedium.copy(
                  fontSize = 14.sp,
                  fontWeight = FontWeight.Medium
               ),
               textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(25.dp))

         }
         Box(
            modifier = Modifier
               .align(Alignment.TopCenter)
               .padding(top = 100.dp)
         ) {
            uiState.error?.message?.let {
               ErrorViewWithoutButton(text = it, visible = showError) {
                  showError = false
               }
            }
         }
      }
   }
}

@Preview(name = "Signup")
@Composable
fun SignUpScreenPreview() {
   PreviewContent {
      SignUpScreenContent(
         uiState = UIState(),
         events = {}
      )
   }
}

@Preview(name = "Signup (Dark)")
@Composable
fun SignUpScreenDarkPreview() {
   PreviewContent(darkTheme = true) {
      SignUpScreenContent(
         uiState = UIState(),
         events = {}
      )
   }
}
