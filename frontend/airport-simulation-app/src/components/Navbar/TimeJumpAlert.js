// import React, { useState } from "react";
// import axios from "axios";
// import {formatDateTime} from "../../utils/formatDateTime";
// import Alert from "bootstrap/js/src/alert";
// import Button from "bootstrap/js/src/button";
//
// const TimeJumpAlert = ({message}) => {
//     document.getElementById('alert-container')
//     const [show, setShow] = useState(true);
//
//     const handleClose = () => {
//         setShow(false);
//     };
//
//     return (
//         <>
//             {show && (
//                 <Alert variant={"primary"} onClose={handleClose} dismissible>
//                     {message}
//                     <Button variant={"link"} onClick={handleClose}>Dismiss</Button>
//                 </Alert>
//             )}
//             {/*<div id="alert-container">*/}
//             {/*    {triggeredEvents.map((event, index) => (*/}
//             {/*        <div*/}
//             {/*            key={index}*/}
//             {/*            className="alert alert-primary alert-dismissible fade show"*/}
//             {/*            role="alert"*/}
//             {/*        >*/}
//             {/*            */}
//             {/*            <button*/}
//             {/*                type="button"*/}
//             {/*                className="btn-close"*/}
//             {/*                data-bs-dismiss="alert"*/}
//             {/*                aria-label="Close"*/}
//             {/*            ></button>*/}
//             {/*        </div>*/}
//             {/*    ))}*/}
//             {/*</div>*/}
//         </>
//     );
// };
//
// export default TimeJump;