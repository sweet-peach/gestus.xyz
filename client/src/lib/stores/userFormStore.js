import {readable, writable} from "svelte/store";

export const isOpen = writable(false);

export const TYPE = Object.freeze({
    CREATE: 'CREATE',
    UPDATE: 'UPDATE',
});

export const formType = writable(TYPE.CREATE);

const initialValue = {
    firstName:"",
    lastName:"",
    email:"",
    password:"",
    role:"",
};

export const formData = writable({...initialValue});
export function resetFormDateStore() {
    formData.set({...initialValue});
}



