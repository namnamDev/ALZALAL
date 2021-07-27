function validatePassword(password,passwordConfirm){
if (password != passwordConfirm) {
    return false;
}else{
    return true;
}
}
export { validatePassword };