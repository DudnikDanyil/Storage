import React from 'react';
import Search from '../../components/Search/Search';
import LogOutButton from '../../components/LogOutButton/LogOutButton';
import './Home.scss'

let Home = () =>{
  return (
    <header className='header'>
         <Search/>
         <LogOutButton/>
    </header>
  );
}

export default Home;
