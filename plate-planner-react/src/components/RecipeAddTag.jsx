import React, {useState} from 'react';
import PropTypes from "prop-types";
import {
  MDBBtn,
  MDBModal,
  MDBModalDialog,
  MDBModalContent,
  MDBModalHeader,
  MDBModalTitle,
  MDBModalBody,
  MDBModalFooter, MDBInput,
} from 'mdb-react-ui-kit';
import Button from "./Button";
import recipeService from "../services/recipeService.js";
import tagService from "../services/tagService.js";
// this will tell the component what props (and what the types are) so intellij doesn't freak out!
App.propTypes = {
  recipe: PropTypes.object,
  stateCounter: PropTypes.number,
  setStateCounter: PropTypes.func,
};
export default function App({recipe, setStateCounter, stateCounter}) {
  const [basicModal, setBasicModal] = useState(false);
  const [tags, setTags] = useState([]);
  const [tagsToSave, setTagsToSave] = useState([]);
  const [tagIdsToSave, setTagIdsToSave] = useState([]);
  const [actionMsg, setActionMsg] = useState(null);
  const [tagToCreate, setTagToCreate] = useState(null);

  const toggleOpen = () => {
    setBasicModal(!basicModal);
    loadTags();
    setActionMsg(null);
    setTagsToSave([]);
  }

  const closeWindow = () => {
    setTags([]);
    setStateCounter(stateCounter + 1);
    toggleOpen();
  }

  const loadTags = () => {
    setActionMsg("Loading Tags...");
    tagService.getAll()
      .then((response) => {
        setTags(response.data);
        setActionMsg(null);
        // console.log("response.data: ", response.data);
      })
      .catch((err) => {
        setActionMsg("There was a problem connecting to the database.  Please try again later.");
        console.log("Error: ", err.message);
      });

  }

  const handleTagSelect = (e) => {
    let chosenTags = [];
    let chosenTagIds = [];
    for (var i = 0; i < e.target.options.length; i++) {
      // console.log("Option: ", e.target.options[i])
      if (e.target.options[i].selected) {
        chosenTags.push(tags[i]);
        chosenTagIds.push(tags[i].id);
      }
    }

    setTagsToSave(chosenTags);
    setTagIdsToSave(chosenTagIds);
    // console.log("chosenTags: ", chosenTags);
    // console.log("chosenTagIds: ", chosenTagIds);
  }

  const createNewTag = () => {
    setActionMsg("Creating new tag...");

    if (tagToCreate) {
      const tagObject = {
        name: tagToCreate,
        description: tagToCreate
      }
      tagService.create(tagObject)
        .then(() => {
          setActionMsg("New tag created.");
          setTags([]);
          loadTags();
          setActionMsg(null);
        })
        .finally(() => {
          setStateCounter(stateCounter + 1);
        })
        .catch((err) => {
          setActionMsg("There was a problem connecting to the database.  Please try again later.");
          console.log("Error: ", err.message);
        });
    }

  }

  const updateRecipe = () => {
    setActionMsg("Updating recipe with tags...");

    if ((tagIdsToSave == null || tagIdsToSave.length === 0) && (recipe.tags == null || recipe.tags.length === 0)) {
      setActionMsg("Please select one or more tags before clicking Update Tags.");
      setTimeout(() => {
        setActionMsg(null);
      }, 3000);
    } else {
      if ((tagIdsToSave == null || tagIdsToSave.length === 0) && recipe.tags != null && recipe.tags.length > 0) {
        setActionMsg("Removing tags from recipe.");
        setTagIdsToSave([]);
      }
      recipeService.updateTagsToRecipe(recipe.id, tagIdsToSave)
        .then(() => {
          if (tagIdsToSave.length > 0) {
            setActionMsg("Tags updated on recipe.");
            recipe.tags = tagsToSave;
            // console.log("recipe: ", recipe);
          } else {
            setActionMsg("Tags removed from recipe.");
            recipe.tags = [];
          }
        })
        .finally(() => {
          setStateCounter(stateCounter + 1);
        })
        .catch((err) => {
          setActionMsg("There was a problem connecting to the database.  Please try again later.");
          console.log("Error: ", err.message);
        });
    }

  };

  return (
    <>
      <Button label="Add Tag" onClick={toggleOpen}/>
      <MDBModal open={basicModal} onClose={() => setBasicModal(false)} tabIndex='-1'>
        <MDBModalDialog>
          <MDBModalContent>
            <MDBModalHeader>
              <MDBModalTitle>Add tags: {recipe.name}</MDBModalTitle>
              <MDBBtn className='btn-close' color='none' onClick={toggleOpen}></MDBBtn>
            </MDBModalHeader>

            <MDBModalBody>

              <MDBInput
                label="Create new tag"
                id="newTag"
                type="text"
                name="newTag"
                onChange={e => setTagToCreate(e.target.value)}
              />
              <Button disabled={tagToCreate === null} label="Create Tag" onClick={createNewTag}/>
              {tags.length > 0 &&
                <div>
                  <label>Select Multiple Tags</label>
                  <div>
                    <select
                      name="tags"
                      id="tags"
                      size={tags.length}
                      multiple
                      onChange={e => handleTagSelect(e)}>
                      {
                        (() => {
                          let container = [];
                          let selected = false;
                          tags.forEach((tag, index) => {
                            for (var i = 0; i < recipe.tags.length; i++) {
                              selected = tag.id == recipe.tags[i].id;
                              if (selected) {
                                break;
                              }
                            }
                            container.push(
                              <option key={index} value={tag.id} title={tag.description}
                                      selected={selected}>{tag.name}</option>);
                          });
                          return container;
                        })()
                      }
                    </select>
                  </div>
                </div>
              }
              {actionMsg != null &&
                <div>{actionMsg}</div>
              }
            </MDBModalBody>

            <MDBModalFooter>
              <Button label="Close" onClick={closeWindow}/>
              {actionMsg == null &&
                <Button label="Update Tags" onClick={updateRecipe}/>
              }
            </MDBModalFooter>
          </MDBModalContent>
        </MDBModalDialog>
      </MDBModal>
    </>
  );
}