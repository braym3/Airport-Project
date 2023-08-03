import {Button, Modal} from "react-bootstrap";

const InfoModal = ({ selectedItem, showModal, setShowModal }) => {
    if(!selectedItem) return null;

    const { title, description, sinceTime, tillTime } = selectedItem;

    return(
        <Modal show={showModal} onHide={() => setShowModal(false)}>
            <Modal.Header closeButton>
                <Modal.Title>{title}</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <p>{description}</p>
                <p>{sinceTime} - {tillTime}</p>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={() => setShowModal(false)}>
                    Close
                </Button>
            </Modal.Footer>
        </Modal>
    );
}

export default InfoModal;