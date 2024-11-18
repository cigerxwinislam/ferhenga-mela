// Bismillahirahmanirahim

"use client";

import { useRef } from "react";

import { useState } from "react";
import Dudu from "../dudu";
import Car from "../car";
import Sise from "../sise";
import Page from "../gotin";

import { Button } from "react-bootstrap";
export default function UploadForm() {
  const [peyam, setpeyam] = useState("")

  
  const fileInput = useRef<HTMLInputElement>(null);

  async function uploadFile(
    evt: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) {
    evt.preventDefault();

    const formData = new FormData();
    formData.append("file", fileInput?.current?.files?.[0]!);

    const response = await fetch("/api", {
      method: "POST",
      body: formData,
    });
    const result = await response.json();
    console.log(result);

    if (result=="success") {
      
setpeyam("yüklendi!")
    } else {
      



      setpeyam("yüklenemedi!")
    }
  }

  return (<div>
    <h1>Yönetici Paneli</h1>
    <Page/>



    <form className="flex flex-col gap-4" encType="multipart/form-data">
      <label>
        <span>1.Fotoğrafı Yükle</span>
        <input  type="file" name="file" ref={fileInput} />
      </label>
      <Button type="submit" onClick={uploadFile}>
        Yükle
      </Button>

      <h1>{peyam} </h1>
    </form>


<br></br><br></br>

<Dudu/>
<br></br><br></br>
<Sise/>
<br></br><br></br>
<Car/>

    </div>
  );
}