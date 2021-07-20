function validatePassword(member_password,passwordConfirm){
if (member_password != passwordConfirm) {
    return false;
}else{
    return true;
}
}
export { validatePassword };