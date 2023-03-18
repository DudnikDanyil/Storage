import './Form.scss';
import React from 'react';
import Registration from './Registration/Registration';

const reg: {title:string} = {title:'Регистрация'}
let Form= () =>{
  return (
    <div className="primary-form">
      <Registration {...reg}/>
    </div>
  );
}

export default Form;
