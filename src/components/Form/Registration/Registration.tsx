import './Registration.scss';
import React from 'react';

interface RegistrationProps {
  title: string
}
let Button = () => 
{
  return(
  <button type="submit">Зареєструватися</button>
  )
}
const Registration = (props:RegistrationProps) =>{
  let [type,setType] = React.useState('password');
  const [active, setImage] = React.useState("1");
     let TypeInputSwitch = () => {
      setType(type === "password" ? "text" : "password") 
      setImage(active === "100" ? "1" : '100')

    }
  return (
    <div className="primary-form__register register-view">
      <form autoComplete="on" className="register-view__action">
        <fieldset>
        <legend aria-hidden="false">Форма реєстрації</legend>
        <h1>{props.title}</h1>
        <input type="email" placeholder="Ведіть почту..." aria-label="Ведіть почту"/>
        <div className="register-view__password-input password-input" >
        <input type={type} className="password-input__item" placeholder="Ведіть пароль..." aria-label="Ведіть пароль"/>
        <button type="button" className="password-input__switch"  onClick={TypeInputSwitch}><span>{active}</span></button>
        </div>
        <Button />
        </fieldset>
      </form>
    </div>
  );
}

export default Registration;
