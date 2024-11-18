// Bismillahirahmanirahim

"use client"

import React from 'react';
import {
  MDBContainer,
  MDBInput,

  MDBBtn,
 
}
from 'mdb-react-ui-kit';

import 'bootstrap/dist/css/bootstrap.css'
import { useState } from 'react';
import { useRouter } from 'next/navigation'
import { Button } from 'react-bootstrap';
function Teketin() {
 const router=useRouter();
  const [passw, setpassw] = useState("");
  const [nav, setnav] = useState("");

  const [nivis, setnivis] = useState("")
  function teketin() {
    
    if (nav=="Akif"&& passw=="akif0434") {
      router.push("/admin/panel")
    }
    else{
      setnivis("Yanlış Girdiniz!")
    }
  }
  return (
    <MDBContainer className="p-3 my-5 d-flex flex-column w-50">

     <h1>Yönetici Paneli</h1>
      <MDBInput wrapperClass='mb-4' label='Adınız' id='form1' type='text' onChange={(e)=>setnav(e.target.value)}/>
      <MDBInput wrapperClass='mb-4' label='Şifreniz' id='form2' type='password' onChange={(e)=>setpassw(e.target.value)}/>

      

      <Button className="mb-4" onClick={teketin}>Giriş Yapın</Button>

      <a style={{color:'red'}}>{nivis}</a>

    </MDBContainer>
  );
}

export default Teketin;