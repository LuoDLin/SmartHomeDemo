package com.luodlin.smarthomedemo.ui.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.DeliveryDining
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.ldl.ouc_iot.ui.components.TextFieldState
import com.luodlin.smarthomedemo.R
import com.luodlin.smarthomedemo.ui.State
import com.luodlin.smarthomedemo.ui.components.TextField
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.regex.Pattern

sealed interface SignInType {
    object Password : SignInType
    object PhoneCode : SignInType
}

@Composable
fun SignInScreen(
    signIn: (String, String, Int) -> Unit,
    signState: State<Nothing>,
    getPhoneCode: () -> Unit,
    getPhoneCodeState: State<Nothing>
) {
    val phoneTextFieldState: TextFieldState = remember { PhoneTextFieldState() }
    val codeTextFieldState: TextFieldState = remember { CodeTextFieldState() }
    val passwordTextFieldState: TextFieldState = remember { PasswordTextFieldState() }
    var signInType: SignInType by remember { mutableStateOf(SignInType.PhoneCode) }
    SignInScreen(
        phoneTextFieldState = phoneTextFieldState,
        codeTextFieldState = codeTextFieldState,
        passwordTextFieldState = passwordTextFieldState,
        signInType = signInType,
        switchSignInType = { odlSignInType: SignInType ->
            if (odlSignInType == SignInType.PhoneCode) signInType = SignInType.Password
            else if (odlSignInType == SignInType.Password) signInType = SignInType.PhoneCode
        },
        signIn = {
            if (signInType == SignInType.Password) {
                signIn(phoneTextFieldState.text, passwordTextFieldState.text, 1)
            } else if (signInType == SignInType.PhoneCode) {
                signIn(phoneTextFieldState.text, codeTextFieldState.text, 0)
            }
        },
        signState = signState,
        getPhoneCode = getPhoneCode,
        getPhoneCodeState = getPhoneCodeState

    )

}

@Composable
fun SignInScreen(
    phoneTextFieldState: TextFieldState,
    codeTextFieldState: TextFieldState,
    passwordTextFieldState: TextFieldState,
    signIn: () -> Unit,
    signState: State<Nothing>,
    signInType: SignInType,
    switchSignInType: (odlSignInType: SignInType) -> Unit,
    getPhoneCode: () -> Unit,
    getPhoneCodeState: State<Nothing>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        SignInLogo()

        Spacer(modifier = Modifier.height(20.dp))

        //账号输入框
        PhoneTextField(textFieldState = phoneTextFieldState)

        Spacer(modifier = Modifier.height(20.dp))

        if (signInType is SignInType.PhoneCode) {//验证码登录
            CodeTextField(
                textFieldState = codeTextFieldState,
                getPhoneCode = getPhoneCode,              //获取验证码
                getPhoneCodeState = getPhoneCodeState,//获取验证码状态
                signIn = signIn //登录
            )
        } else if (signInType is SignInType.Password) { //密码登录
            PasswordTextField(textFieldState = passwordTextFieldState, signIn = signIn)
        }

        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = signIn, modifier = Modifier.fillMaxWidth()) {
            Row {
                if (signState == State.Loading) {
                    CircularProgressIndicator(
                        strokeWidth = 2.dp,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(18.dp)
                            .align(Alignment.CenterVertically),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Text(
                    stringResource(id = R.string.signin), style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        SwitchSignInType(signInType = signInType, switchSignInType = switchSignInType)
        Spacer(modifier = Modifier.weight(7f))
    }
}

@Composable
fun SwitchSignInType(
    signInType: SignInType, switchSignInType: (odlSignInType: SignInType) -> Unit
) {
    Text(
        text = if (signInType is SignInType.Password) stringResource(id = R.string.pswdLogin) else stringResource(
            id = R.string.codeLogin
        ),
        modifier = Modifier.clickable { switchSignInType(signInType) },
        style = TextStyle(color = MaterialTheme.colorScheme.primary),
    )

}


fun passwordValidator(text: String): Boolean {
    return (text.length >= 6)
}

fun passwordErrorFor(text: String): String {
    return if (text.isEmpty()) "输入密码为空"
    else "密码长度小于6"
}

class PasswordTextFieldState :
    TextFieldState(validator = ::passwordValidator, errorFor = ::passwordErrorFor)

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = PasswordTextFieldState(),
    signIn: () -> Unit = {}
) {
    PasswordTextField(
        modifier = modifier,
        textFieldState = textFieldState,
        imeAction = ImeAction.Done,
        keyboardActions = KeyboardActions(onDone = {
            textFieldState.enableShowErrors()
            if (!textFieldState.isError) signIn()
        })
    )
}

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = PasswordTextFieldState(),
    label: String = stringResource(
        id = R.string.password
    ),
    maxLength: Int = 16,
    imeAction: ImeAction,
    keyboardActions: KeyboardActions
) {
    val showPassword = rememberSaveable { mutableStateOf(false) }
    TextField(
        modifier = modifier,
        textFieldState = textFieldState,
        label = label,
        maxLength = maxLength,
        trailingIcon = {
            IconButton(
                onClick = { showPassword.value = !showPassword.value },
                modifier = Modifier.padding(end = 5.dp)
            ) {
                Icon(
                    imageVector = if (showPassword.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                    contentDescription = "",
                )
            }
        },
        keyboardType = KeyboardType.Password,
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        imeAction = imeAction,
        keyboardActions = keyboardActions
    )
}

fun phoneValidator(text: String): Boolean {
    return Pattern.matches("1[3-9]\\d{9}", text)
}

fun phoneErrorFor(text: String): String {
    return if (text.isEmpty()) "输入号码为空"
    else "无效号码:$text"
}

class PhoneTextFieldState : TextFieldState(validator = ::phoneValidator, errorFor = ::phoneErrorFor)

@Composable
fun PhoneTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = PhoneTextFieldState(),
) {
    PhoneTextField(
        modifier = modifier,
        textFieldState = textFieldState,
        imeAction = ImeAction.Next,
        keyboardActions = KeyboardActions(onNext = {
            textFieldState.enableShowErrors()
            if (!textFieldState.isError) defaultKeyboardAction(ImeAction.Next)
        })
    )

}


@Composable
fun PhoneTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = PhoneTextFieldState(),
    label: String = stringResource(id = R.string.phone),
    maxLength: Int = 11,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    imeAction: ImeAction
) {
    TextField(
        modifier = modifier,
        textFieldState = textFieldState,
        label = label,
        maxLength = maxLength,
        filter = { it.isDigit() },
        imeAction = imeAction,
        keyboardType = KeyboardType.Phone,
        keyboardActions = keyboardActions
    )
}

fun phoneCodeValidator(text: String): Boolean {
    return text.length == 6
}

fun phoneCodeErrorFor(text: String): String {
    return if (text.isEmpty()) "验证码为空"
    else "验证码错误"
}

class CodeTextFieldState :
    TextFieldState(validator = ::phoneCodeValidator, errorFor = ::phoneCodeErrorFor)

@Composable
fun CodeTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = CodeTextFieldState(),
    getPhoneCodeState: State<Nothing> = State.None,
    getPhoneCode: () -> Unit = {},
    signIn: () -> Unit = {}
) {
    CodeTextField(
        modifier = modifier,
        textFieldState = textFieldState,
        getPhoneCodeState = getPhoneCodeState,
        getPhoneCode = getPhoneCode,
        imeAction = ImeAction.Done,
        keyboardActions = KeyboardActions(onDone = {
            textFieldState.enableShowErrors()
            if (!textFieldState.isError) signIn()
        })
    )
}

@Composable
fun CodeTextField(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = CodeTextFieldState(),
    maxLength: Int = 6,
    label: String = stringResource(id = R.string.phoneCode),
    imeAction: ImeAction,
    keyboardActions: KeyboardActions,
    getPhoneCode: () -> Unit = {},
    getPhoneCodeState: State<Nothing> = State.None
) {
    var remainingTime by remember { mutableStateOf(0) }
    val resources = LocalContext.current.resources
    var showText by remember {
        mutableStateOf(
            resources.getString(R.string.getPhoneCode)
        )
    }
    LaunchedEffect(getPhoneCodeState) {
        if (getPhoneCodeState is State.Success) {
            remainingTime = 60
            launch {
                while (remainingTime > 0) {
                    showText = resources.getString(R.string.resendAfter, remainingTime)
                    delay(1000)
                    remainingTime--
                }
                showText = resources.getString(R.string.resend)

            }
        }
    }

    TextField(
        modifier = modifier,
        textFieldState = textFieldState,
        maxLength = maxLength,
        label = label,
        trailingIcon = {
            TextButton(
                modifier = Modifier.padding(end = 5.dp),
                onClick = { getPhoneCode() },
                enabled = getPhoneCodeState != State.Loading && remainingTime == 0,
                shape = RoundedCornerShape(5.dp)
            ) {
                if (getPhoneCodeState == State.Loading) {
                    CircularProgressIndicator(
                        strokeWidth = 3.dp,
                        modifier = Modifier.size(22.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                } else {
                    Text(showText)
                }
            }
        },
        keyboardType = KeyboardType.Number,
        imeAction = imeAction,
        keyboardActions = keyboardActions
    )
}


@Composable
fun SignInLogo() {
    Icon(
        imageVector = Icons.Outlined.DeliveryDining,
        contentDescription = "",
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(100.dp)
    )

    Spacer(modifier = Modifier.height(20.dp))

    Text(stringResource(id = R.string.logo), style = MaterialTheme.typography.headlineLarge)
}


