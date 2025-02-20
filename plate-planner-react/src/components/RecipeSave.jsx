import React, { useState } from 'react';
import {
  MDBBtn,
  MDBModal,
  MDBModalDialog,
  MDBModalContent,
  MDBModalHeader,
  MDBModalTitle,
  MDBModalBody,
  MDBModalFooter,
} from 'mdb-react-ui-kit';
import Button from "./Button.jsx";
import recipeService from "../services/recipeService.js";
import Cookies from 'js-cookie';
import { Link } from 'react-router-dom';



export default function App(props) {
    const [basicModal, setBasicModal] = useState(false);
    const [actionMsg, setActionMsg] = useState(null);
    const userName = Cookies.get('username');

    const toggleOpen = () => {
        setBasicModal(!basicModal);
        setActionMsg(null);
        saveRecipe();
    }

    const closeWindow = () => {
        setBasicModal(false);
    }

    const saveRecipe = () => {
        if (userName == null || userName.length == 0) {
            setActionMsg("Please login to save recipes.");
        } else {
            setActionMsg("Saving recipe for " + userName + "...");
            recipeService.create(props.recipe)      
                .then((response) => {
                    setActionMsg('Recipe saved successfully for ' + userName + '.');
                })
                .catch((err) => {
                    setActionMsg("There was a problem connecting to the database.  Please try again later.");
                    console.log("Error: ", err.message);
                });
        }
    }

    return (
        <>
            <Button label="Save Recipe" onClick={toggleOpen} />
            <MDBModal open={basicModal} onClose={() => setBasicModal(false)} tabIndex='-1'>
                <MDBModalDialog>
                    <MDBModalContent>
                        <MDBModalHeader>
                            <MDBModalTitle>Save recipe: {props.recipe.name}</MDBModalTitle>
                            <MDBBtn className='btn-close' color='none' onClick={toggleOpen}></MDBBtn>
                        </MDBModalHeader>

                        <MDBModalBody>
                            <div>{actionMsg}</div>
                       </MDBModalBody>

                        <MDBModalFooter>
                            {userName != null &&
                                <Link className="btn btn-primary" to="/saved-recipes">Saved Recipes</Link>
                            }
                            {userName == null &&
                                <Link className="btn btn-primary" to="/login">Login/Register</Link>
                            }      
                            <Button label="Close" onClick={closeWindow} />                      
                        </MDBModalFooter>
                    </MDBModalContent>
                </MDBModalDialog>
            </MDBModal>
        </>
  );
}