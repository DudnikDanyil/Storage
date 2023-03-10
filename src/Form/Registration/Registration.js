import './Registration.css';

function TypeInputSwitcher()
{
const INPUT_ATTR = document.querySelector('.password-input__item');
const SWITCHER = document.querySelector('.password-input__switcher');
let type = INPUT_ATTR.getAttribute('type')

if(type === 'password'){
  INPUT_ATTR.setAttribute('type', 'text')
  SWITCHER.setAttribute('aria-checked', 'true')
  SWITCHER.classList.add('password-input__switcher--active')
}
else{
  INPUT_ATTR.setAttribute('type', 'password')
  SWITCHER.setAttribute('aria-checked', 'false')
  SWITCHER.classList.remove('password-input__switcher--active')
}
};


let Registration = () =>{
  return (
    <div className="primary-form__register register-view">
      <form autoComplete="on" className="register-view__action">
        <fieldset>
        <legend aria-hidden="false">Форма реєстрації</legend>
        <h1>Реєстрації</h1>
        <input type="email" className="rrr" placeholder="Ведіть почту..." aria-label="Ведіть почту" tab="0"/>
        <div className="register-view__password-input password-input" >
        <input type="password" className="password-input__item" placeholder="Ведіть пароль..." aria-label="Ведіть пароль" tab="1"/>
        <button type="button" className="password-input__switcher"  onClick={TypeInputSwitcher} role="radio" aria-checked="false"></button>
        </div>
        <button type="submit">Зареєструватися</button>
        </fieldset>
      </form>
    </div>
  );
}

export default Registration;
