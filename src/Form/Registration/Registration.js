import './Registration.css';
import React from 'react';
// function TypeInputSwitch()
// {
// const INPUT_ATTR = document.querySelector('.password-input__item');
// const SWITCH = document.querySelector('.password-input__switch');
// let type = INPUT_ATTR.getAttribute('type')

// if(type === 'password'){
//   INPUT_ATTR.setAttribute('type', 'text')
//   SWITCH.setAttribute('aria-checked', 'true')
//   SWITCH.classList.add('password-input__switch--active')
// }
// else{
//   INPUT_ATTR.setAttribute('type', 'password')
//   SWITCH.setAttribute('aria-checked', 'false')
//   SWITCH.classList.remove('password-input__switch--active')
// }
// };


let Registration = () =>{
  let [type,setType] = React.useState('password');
  const [activeClass, setClassName] = React.useState('');
     let TypeInputSwitch = () => {
      setType(type === "password" ? "text" : "password") 
      setClassName(activeClass === '' ? "password-input__switch--active" : '')
    }
  return (
    <div className="primary-form__register register-view">
      <form autoComplete="on" className="register-view__action">
        <fieldset>
        <legend aria-hidden="false">Форма реєстрації</legend>
        <h1>Реєстрації</h1>
        <input type="email" placeholder="Ведіть почту..." aria-label="Ведіть почту" tab="0"/>
        <div className="register-view__password-input password-input" >
        <input type={type} className="password-input__item" placeholder="Ведіть пароль..." aria-label="Ведіть пароль" tab="1"/>
        <button type="button" className={`password-input__switch ${activeClass}`}  onClick={TypeInputSwitch}></button>
        </div>
        <button type="submit">Зареєструватися</button>
        </fieldset>
      </form>
    </div>
  );
}

export default Registration;
