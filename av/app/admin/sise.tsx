// Bismillahirahmanirahim

"use client";

import { useRef } from "react";

import { useState } from "react";

import { Button } from "react-bootstrap";
export default function Sise() {
  const [peyam, setpeyam] = useState("")

  
  const fileInput = useRef<HTMLInputElement>(null);

  async function uploadFile(
    evt: React.MouseEvent<HTMLButtonElement, MouseEvent>
  ) {
    evt.preventDefault();

    const formData = new FormData();
    formData.append("file", fileInput?.current?.files?.[0]!);

    const response = await fetch("/api/sise", {
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

  return (
    <form className="flex flex-col gap-4">
      <label>
        <span>3.ü Fotoğrafı Yükle</span>
        <input type="file" name="file" ref={fileInput} />
      </label>
      <Button type="submit" onClick={uploadFile}>
        Yükle
      </Button>

      <h1>{peyam} </h1>
    </form>
  );
}