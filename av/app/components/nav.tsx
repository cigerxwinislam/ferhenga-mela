// Bismillahirahmanirahim

"use client"

import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import NavDropdown from 'react-bootstrap/NavDropdown';
import 'bootstrap/dist/css/bootstrap.css'
function BasicExample() {
  return (
    <Navbar expand="lg" className="bg-body-tertiary">
      <Container>
        <Navbar.Brand href="#home">Barış Sürücü Kursu</Navbar.Brand>
        <Navbar.Toggle aria-controls="basic-navbar-nav" />
        <Navbar.Collapse id="basic-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link href="/">Anasayfa</Nav.Link>
            <Nav.Link href="/kurumsal">Kurumsal</Nav.Link>
            <NavDropdown title="Hizmetler" id="basic-nav-dropdown">
              <NavDropdown.Item href="/hizmetler/">Projeler</NavDropdown.Item>
              <NavDropdown.Item href="hizmetler/referans">
                Referanslar
              </NavDropdown.Item>
              <NavDropdown.Item href="hizmetler/uygulama">
              Uygulamalar</NavDropdown.Item>
            
            </NavDropdown>
            <Nav.Link href="/iletisim">İletişim</Nav.Link>

          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
}

export default BasicExample;