// Bismillahirahmanirahim



import React from 'react'
import { Container, Row ,Col,Alert} from 'react-bootstrap'
import KitchenSinkExample from './components/card'

import mmwene from '../public/mmr.jpg'
function page() {
  return (
    <div>

    
      <Container>
      <Alert>
  
  <Alert style={{background:"white"}}>Hayatınıza değer verin..</Alert>  
  
  <Alert style={{background:"white"}}>Ehliyet Kursu</Alert>  
    </Alert>  
<Row>

<Col>  

<KitchenSinkExample nav="Kaliteli Eğitim" wene={mmwene}  nivis="Bir inşaatın bütün mimarisi..."/>

<KitchenSinkExample nav="Mobilya Tasarımı" wene={mmwene} />
</Col>
<Col>  

<KitchenSinkExample nav ="İç Mimar-Dekorasyon" wene={mmwene}/>

<KitchenSinkExample nav="Genel Yaklaşım" wene={mmwene}/>
</Col>
<Alert>
  
<Alert></Alert>  

<Alert></Alert>  
  </Alert>  
  <Col>  

<KitchenSinkExample/>

<KitchenSinkExample/>
</Col>
<Col>  

<KitchenSinkExample/>

<KitchenSinkExample/>
</Col>
<Alert>
  
<Alert> Açıklama</Alert>  

<Alert> mm ..Açıklama.....</Alert>  
  </Alert>  

</Row>




      </Container>
    </div>
  )
}

export default page