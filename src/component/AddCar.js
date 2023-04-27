import React, { useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';

// useState 후크를 이용해 모든 자동차 필드를 포함하는 상태를 선언함, 이 대화상자에도 대화상자 폼의 표시 여부를 정의하는 부울 상태인 open 이 필요함
function AddCar(props) {
    const [open, setOpen] = useState(false);
    const [car, setCar] = useState({
        brand: '',
        model: '',
        color: '',
        year: '',
        fuel: '',
        price: ''
    });
    // 대화상자 폼을 닫고 여는 두 함수를 추가해야함 
    const handleClickOpen = () => {
        setOpen(true);
    };
    // 모달 폼 닫기
    const handleClose = () => {
        setOpen(false);
    };
    // 자동차를 저장하고 모달  폼을 닫음
    const handleSave = () => {
        props.addCar(car);
        handleClose();
    }
    
    const handleChange = (event) => {
        setCar({...car, [event.target.name]: event.target.value});
    }

    return(
        <div>
            <Button variant="contained" onClick={handleClickOpen}>
                    New Car
                </Button>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>New Car</DialogTitle>
                <DialogContent>
                    <Stack spacing={2} mt={1}> 
                        <TextField label="Brand" name="brand" // TextField 컴포넌트로 모달 폼 변경 , Stack 으로 간격 맞춤 
                            autoFocus
                            variant="standard" value={car.brand}
                            onChange={handleChange} />
                        <TextField label="Model" name="model"
                            autoFocus
                            variant="standard" value={car.model}
                            onChange={handleChange} />
                        <TextField label="Color" name="color"
                            autoFocus
                            variant="standard" value={car.color}
                            onChange={handleChange} />
                        <TextField label="Year" name="year"
                            autoFocus
                            variant="standard" value={car.year}
                            onChange={handleChange} />
                        <TextField label="Price" name="price"
                            autoFocus
                            variant="standard" value={car.price}
                            onChange={handleChange} />    
                    </Stack>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={handleSave}>Save</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}
export default AddCar;