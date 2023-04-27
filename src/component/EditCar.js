import React, { useState } from 'react';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import Button from '@mui/material/Button';
import IconButton from '@mui/material/IconButton';
import EditIcon from '@mui/icons-material/Edit';

// 표의 각 행에 편집을 시작하는 Edit 버튼을 추가
// Edit 버튼을 누르면 기존 자동차를 편집하고 변경 내용을 저장할수 있는 모달 폼이 열림
function EditCar(props) {
    const [open, setOpen] = useState(false);
    const [car, setCar] = useState({
        brand: '', model: '', color: '',
        year: '', fule:'', price:''
    });

    // 모달 폼을 열고 car 상태를 업데이트
    // 편집할 자동차는 폼을 기존 자동차 데이터로 채우는 데 이용하는 data 프롭에서 가져옴
    // 이제 폼이 열리면 car 상태가 data 프롭의 값으로 업데이트 된다.
    const handleClickOpen = () => {
        setCar ({
            brand: props.data.row.brand,
            model: props.data.row.model,
            color: props.data.row.color,
            year: props.data.row.year,
            fule: props.data.row.fule,
            price: props.data.row.price
        })
        setOpen(true);
    }


    //모달 폼 닫기
    const handleClose = () => {
        setOpen(false);
    };

    const handleChange = (event) => {
        setCar({...car,
            [event.target.name]: event.target.value});

        }
    
    // 자동차를 업데이트하고 모달 폼을 닫음
    // props 를 통해 updateCar 함수를 호출, 첫번째 인수는 car 상태이며, 업데이트된 자동차 객체가 여기에 들어있음
    // 두번째 인수는 data 프롭의 id 속성이며 자동차에 대한 링크임
    const handleSave = () => {
        props.updateCar (car, props.data.id);
            handleClose();
    }
    

    //return 문에서 편집 대화상자 폼을 렌더링 함.
    return(
        <div>
            <IconButton onClick={handleClickOpen}>
                <EditIcon color="primary" />
            </IconButton>
            <Dialog open={open} onClose={handleClose}>
                <DialogTitle>Edit car</DialogTitle>
                <DialogContent>
                    <input placeholder='Brand' name ="brand"
                        value={car.brand}onChange={handleChange}/>
                    <br/>
                    <input placeholder='Model' name ="model"
                        value={car.model}onChange={handleChange}/>
                    <br/>
                    <input placeholder='Color' name ="color"
                        value={car.color}onChange={handleChange}/>
                    <br/>
                    <input placeholder='Year' name ="year"
                        value={car.year}onChange={handleChange}/>
                    <br/>
                    <input placeholder='Price' name ="price"
                        value={car.price}onChange={handleChange}/>
                    <br/>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}> Cancel </Button>
                    <Button onClick={handleSave}> Save</Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default EditCar;