// Bismillahirahmanirahim



import { FormEvent } from 'react'
import { Button } from 'react-bootstrap'
 
export default function Page() {
  async function onSubmit(event: FormEvent<HTMLFormElement>) {
    event.preventDefault()
 
    const formData = new FormData(event.currentTarget)
    const response = await fetch('/api/submit', {
      method: 'POST',
      body: formData,
    })
 
    // Handle response if necessary
    const data = await response.json()
    // ...
  }
 
  return (
    <form onSubmit={onSubmit}>

      <input type="text" placeholder='1.yazıyı yazın' name="name1" />
<br></br>
      <input type="text" placeholder='2.yazıyı yazın' name="name2" />
      <br></br>
      <input type="text" placeholder='3.yazıyı yazın' name="name3" />
      <br></br>
      <input type="text" placeholder='4.yazıyı yazın'name="name4" />
      <br></br>
      <Button type="submit">Yazıları Gönder</ Button>
    </form>
  )
}