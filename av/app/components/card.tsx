// Bismillahirahmanirahim

"use client"

import Card from 'react-bootstrap/Card';
import ListGroup from 'react-bootstrap/ListGroup';
import 'bootstrap/dist/css/bootstrap.css'

import Image from 'next/image';

import nozdeh from '/public/19.jpeg'
import { Button } from 'react-bootstrap';
function KitchenSinkExample(props:any) {
  return (
    <Card style={{ width: '25rem' }}>
 <Image
      src={props.wene}
      alt="Picture of the author"
       width={397} 
      height={397} 
      // blurDataURL="data:..." automatically provided
      // placeholder="blur" // Optional blur-up while loading
    />
      <Card.Body>
        <Card.Title>{props.nav}</Card.Title>
        <Card.Text>
     {props.nivis}
        </Card.Text>
      </Card.Body>
    
      <Card.Body>



    <Button href='/hizmetler'>{props.bttn}</Button>
      </Card.Body>
    </Card>
  );
}

export default KitchenSinkExample;